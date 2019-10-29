package com.yixiekeji.model.sale;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.dao.shop.read.sale.SaleMemberReadDao;
import com.yixiekeji.dao.shop.write.sale.SaleMemberWriteDao;
import com.yixiekeji.entity.sale.SaleMember;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class SaleMemberModel {

    @Resource
    private SaleMemberWriteDao           saleMemberWriteDao;

    @Resource
    private SaleMemberReadDao            saleMemberReadDao;

    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
     * 根据id取得用户推广表
     * @param  saleMemberId
     * @return
     */
    public SaleMember getSaleMemberById(Integer saleMemberId) {
        return saleMemberReadDao.get(saleMemberId);
    }

    /**
     * 保存用户推广表
     * @param  saleMember
     * @return
     */
    public Integer saveSaleMember(SaleMember saleMember) {
        this.dbConstrains(saleMember);
        return saleMemberWriteDao.insert(saleMember);
    }

    /**
     * 更新用户推广表
     * @param  saleMember
     * @return
     */
    public Integer updateSaleMember(SaleMember saleMember) {
        this.dbConstrains(saleMember);
        return saleMemberWriteDao.update(saleMember);
    }

    private void dbConstrains(SaleMember saleMember) {
        saleMember.setMemberName(StringUtil.dbSafeString(saleMember.getMemberName(), true, 50));
        saleMember.setCertificateCode(
                StringUtil.dbSafeString(saleMember.getCertificateCode(), false, 50));
        saleMember.setBankCode(StringUtil.dbSafeString(saleMember.getBankCode(), false, 20));
        saleMember.setBankName(StringUtil.dbSafeString(saleMember.getBankName(), false, 50));
        saleMember.setBankAdd(StringUtil.dbSafeString(saleMember.getBankAdd(), false, 200));
        saleMember.setReferrerName(StringUtil.dbSafeString(saleMember.getReferrerName(), true, 50));
        saleMember.setReferrerCode(StringUtil.dbSafeString(saleMember.getReferrerCode(), true, 20));
        saleMember
                .setReferrerPname(StringUtil.dbSafeString(saleMember.getReferrerPname(), true, 50));
        saleMember.setSaleCode(StringUtil.dbSafeString(saleMember.getSaleCode(), false, 20));
        saleMember.setAudit(StringUtil.dbSafeString(saleMember.getAudit(), true, 200));
        saleMember.setAuditName(StringUtil.dbSafeString(saleMember.getAuditName(), true, 50));
    }

    /**
     * 根据用户ID查询用户推广表
     * @param memberId
     * @return
     */
    public SaleMember getSaleMemberByMemberId(Integer memberId) {
        return saleMemberReadDao.getSaleMemberByMemberId(memberId);
    }

    /**
     * 通过用户ID开启推广员
     * @param saleMember
     * @return
     */
    public Integer saveSaleMemberByMemberId(SaleMember saleMember) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        int success = 0;
        try {
            SaleMember saleMemberOld = saleMemberWriteDao
                    .getSaleMemberByMemberId(saleMember.getMemberId());
            if (saleMemberOld == null) {//没有创建，创建
                this.dbConstrains(saleMember);
                success = saleMemberWriteDao.insert(saleMember);

                //更新推广码，默认用户ID加上100000
                SaleMember saleMemberNew = saleMemberWriteDao.get(saleMember.getId());
                saleMemberNew.setSaleCode((100000 + saleMemberNew.getId().intValue()) + "");
                success = saleMemberWriteDao.update(saleMemberNew);
            } else {//已经创建更新
                saleMemberOld.setSaleCode((100000 + saleMemberOld.getId().intValue()) + "");
                saleMemberOld.setIsSale(ConstantsEJS.YES_NO_1);
                success = saleMemberWriteDao.update(saleMemberOld);
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return success;
    }

    /**
     * 查询推广用户列表
     * @param queryMap
     * @param pager
     * @return
     */
    public List<SaleMember> getSaleMembers(Map<String, String> queryMap, PagerInfo pager) {
        Integer start = 0, size = 0;

        if (pager != null) {
            pager.setRowsCount(saleMemberReadDao.getSaleMembersCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        return saleMemberReadDao.getSaleMembers(queryMap, start, size);
    }

    /**
     * 根据推广码来获取推广用户
     * @param saleCode
     * @return
     */
    public SaleMember getSaleMemberBySaleCode(String saleCode) {
        return saleMemberReadDao.getSaleMemberBySaleCode(saleCode);
    }

    public List<SaleMember> checkSaleCode(String saleCode) {
        return saleMemberReadDao.checkSaleCode(saleCode);
    }

    public SaleMember getAuthInfoById(Integer saleMemberId, Integer state) {
        return saleMemberReadDao.getAuthInfoById(saleMemberId,state);
    }
}