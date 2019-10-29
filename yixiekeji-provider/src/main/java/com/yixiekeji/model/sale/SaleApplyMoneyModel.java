package com.yixiekeji.model.sale;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.sale.SaleApplyMoneyReadDao;
import com.yixiekeji.dao.shop.read.sale.SaleMemberReadDao;
import com.yixiekeji.dao.shop.read.sale.SaleOrderReadDao;
import com.yixiekeji.dao.shop.write.sale.SaleApplyMoneyWriteDao;
import com.yixiekeji.dao.shop.write.sale.SaleMemberWriteDao;
import com.yixiekeji.dao.shop.write.sale.SaleOrderWriteDao;
import com.yixiekeji.entity.sale.SaleApplyMoney;
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.entity.sale.SaleOrder;

@Component
public class SaleApplyMoneyModel {

    @Resource
    private SaleApplyMoneyWriteDao       saleApplyMoneyWriteDao;

    @Resource
    private SaleApplyMoneyReadDao        saleApplyMoneyReadDao;

    @Resource
    private SaleMemberWriteDao           saleMemberWriteDao;

    @Resource
    private SaleMemberReadDao            saleMemberReadDao;

    @Resource
    private SaleOrderWriteDao            saleOrderWriteDao;

    @Resource
    private SaleOrderReadDao             saleOrderReadDao;

    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
     * 根据id取得提款申请表
     * @param  saleApplyMoneyId
     * @return
     */
    public SaleApplyMoney getSaleApplyMoneyById(Integer saleApplyMoneyId) {
        return saleApplyMoneyWriteDao.get(saleApplyMoneyId);
    }

    /**
     * 保存提款申请表
     * @param  saleApplyMoney
     * @return
     */
    public Integer saveSaleApplyMoney(SaleApplyMoney saleApplyMoney) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        int success = 0;
        try {
            this.dbConstrains(saleApplyMoney);
            //判断财务信息是否适合通过
            SaleMember saleMember = saleMemberWriteDao
                .getSaleMemberByMemberId(saleApplyMoney.getMemberId());
            if (saleMember.getState().intValue() != SaleMember.STATE_3) {
                throw new BusinessException("请先提交财务信息，审核通过之后才能进行提现。");
            }
            //判断没有未处理的提款才可以处理
            int count = saleApplyMoneyWriteDao.countSaleApplyMoneyByMemberIdAndState(
                saleApplyMoney.getMemberId(), ConstantsEJS.YES_NO_0);
            if (count > 0) {
                throw new BusinessException("有未处理的退款申请，处理完成之后才能提款。");
            }
            saleApplyMoney.setCertificateCode(saleMember.getCertificateCode());
            saleApplyMoney.setBankType(saleMember.getBankType());
            saleApplyMoney.setBankCode(saleMember.getBankCode());
            saleApplyMoney.setBankName(saleMember.getBankName());
            saleApplyMoney.setBankAdd(saleMember.getBankAdd());

            BigDecimal money = saleOrderWriteDao.sumSaleOrderBySaleStateAndMemberId(
                SaleOrder.SALE_STATE_2, saleApplyMoney.getMemberId());
            if (null == money || "0".equals(money.toString())) {
                throw new BusinessException("没有待提款的订单。");
            }
            saleApplyMoney.setMoney(money);

            success = saleApplyMoneyWriteDao.insert(saleApplyMoney);

            saleOrderWriteDao.updateBySaleStateAndApplyMoney(saleApplyMoney.getId(),
                saleApplyMoney.getMemberId());

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return success;
    }

    /**
    * 更新提款申请表
    * @param  saleApplyMoney
    * @return
    */
    public Integer updateSaleApplyMoney(SaleApplyMoney saleApplyMoney) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        int success = 0;
        try {
            this.dbConstrains(saleApplyMoney);
            saleOrderWriteDao.updateBySaleState(SaleOrder.SALE_STATE_3, SaleOrder.SALE_STATE_4,
                saleApplyMoney.getMemberId());
            success = saleApplyMoneyWriteDao.update(saleApplyMoney);

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return success;
    }

    private void dbConstrains(SaleApplyMoney saleApplyMoney) {
        saleApplyMoney
            .setMemberName(StringUtil.dbSafeString(saleApplyMoney.getMemberName(), false, 50));
        saleApplyMoney.setCertificateCode(
            StringUtil.dbSafeString(saleApplyMoney.getCertificateCode(), false, 50));
        saleApplyMoney.setBankType(StringUtil.dbSafeString(saleApplyMoney.getBankType(), true, 50));
        saleApplyMoney.setBankCode(StringUtil.dbSafeString(saleApplyMoney.getBankCode(), true, 20));
        saleApplyMoney.setBankName(StringUtil.dbSafeString(saleApplyMoney.getBankName(), true, 50));
        saleApplyMoney.setBankAdd(StringUtil.dbSafeString(saleApplyMoney.getBankAdd(), true, 200));
        saleApplyMoney.setBake(StringUtil.dbSafeString(saleApplyMoney.getBake(), false, 200));
        saleApplyMoney
            .setUpdateName(StringUtil.dbSafeString(saleApplyMoney.getUpdateName(), false, 50));
    }

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    public List<SaleApplyMoney> getSaleApplyMoneys(Map<String, String> queryMap, PagerInfo pager) {
        Integer start = 0, size = 0;

        if (pager != null) {
            pager.setRowsCount(saleApplyMoneyReadDao.getSaleApplyMoneysCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        return saleApplyMoneyReadDao.getSaleApplyMoneys(queryMap, start, size);
    }
}