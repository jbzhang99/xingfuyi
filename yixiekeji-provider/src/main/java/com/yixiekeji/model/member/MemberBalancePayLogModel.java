package com.yixiekeji.model.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.write.member.MemberBalancePayLogWriteDao;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberBalanceLogs;
import com.yixiekeji.entity.member.MemberBalancePayLog;

@Component
public class MemberBalancePayLogModel {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory
        .getLogger(MemberBalancePayLogModel.class);

    @Resource
    private MemberBalancePayLogWriteDao    memberBalancePayLogWriteDao;
    @Resource
    private DataSourceTransactionManager   transactionManager;
    @Resource
    private MemberModel                    memberModel;

    /**
     * 根据id取得会员充值记录
     * @param  memberBalancePayLogId
     * @return
     */
    public MemberBalancePayLog getMemberBalancePayLogById(Integer memberBalancePayLogId) {
        return memberBalancePayLogWriteDao.get(memberBalancePayLogId);
    }

    /**
     * 保存会员充值记录
     * @param  memberBalancePayLog
     * @return
     */
    public Integer saveMemberBalancePayLog(MemberBalancePayLog memberBalancePayLog) {
        this.dbConstrains(memberBalancePayLog);
        return memberBalancePayLogWriteDao.save(memberBalancePayLog);
    }

    /**
    * 更新会员充值记录
    * @param  memberBalancePayLog
    * @return
    */
    public Integer updateMemberBalancePayLog(MemberBalancePayLog memberBalancePayLog) {
        this.dbConstrains(memberBalancePayLog);
        return memberBalancePayLogWriteDao.update(memberBalancePayLog);
    }

    private void dbConstrains(MemberBalancePayLog memberBalancePayLog) {
        memberBalancePayLog.setPaymentCode(
            StringUtil.dbSafeString(memberBalancePayLog.getPaymentCode(), false, 100));
        memberBalancePayLog.setPaymentName(
            StringUtil.dbSafeString(memberBalancePayLog.getPaymentName(), false, 100));
        memberBalancePayLog
            .setPaySn(StringUtil.dbSafeString(memberBalancePayLog.getPaySn(), true, 100));
        memberBalancePayLog
            .setTradeSn(StringUtil.dbSafeString(memberBalancePayLog.getTradeSn(), true, 100));
        memberBalancePayLog.setMemberName(
            StringUtil.dbSafeString(memberBalancePayLog.getMemberName(), false, 100));
    }

    public List<MemberBalancePayLog> page(Map<String, String> queryMap,
                                          PagerInfo pager) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>(queryMap);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(memberBalancePayLogWriteDao.getCount(param));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        param.put("start", start);
        param.put("size", size);
        List<MemberBalancePayLog> list = memberBalancePayLogWriteDao.page(param);
        return list;
    }

    public Integer del(Integer id) {
        return memberBalancePayLogWriteDao.del(id);
    }

    public MemberBalancePayLog getByOrderSn(String orderNo) {
        log.debug("订单号：" + orderNo);
        return memberBalancePayLogWriteDao.getByOrderSn(orderNo);
    }

    public Boolean payAfter(String orderNo, String amount, String tradeSn) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //1.更新充值记录状态
            MemberBalancePayLog paylog = getByOrderSn(orderNo);
            if (paylog == null) {
                throw new BusinessException("找不到支付记录");
            }
            if (paylog.getPayState().intValue() == MemberBalancePayLog.PAY_SUCCESS) {
                //                throw new BusinessException("该订单已支付");
                log.debug("该订单已支付，直接返回");
                return true;
            }
            //            if (paylog.getPayMoney().doubleValue() != Double.valueOf(amount)) {
            //                throw new BusinessException("充值金额异常");
            //            }
            paylog.setPayFinishTime(new Date());
            paylog.setPayState(MemberBalancePayLog.PAY_SUCCESS);
            paylog.setTradeSn(tradeSn);
            memberBalancePayLogWriteDao.update(paylog);

            //2.创建余额日志、更新用户余额
            MemberBalanceLogs mblog = new MemberBalanceLogs();
            mblog.setState(MemberBalanceLogs.STATE_1);
            mblog.setMemberId(paylog.getMemberId());
            mblog.setMemberName(paylog.getMemberName());
            mblog.setMoney(paylog.getPayMoney());
            mblog.setOptId(paylog.getMemberId());
            mblog.setOptName(paylog.getMemberName());
            mblog.setRemark("会员充值");
            memberModel.updateMemberBalance(mblog);

            log.debug("==========支付成功==========");
            transactionManager.commit(status);

            return true;
        } catch (Exception e) {
            transactionManager.rollback(status);
            e.printStackTrace();
        }
        return false;
    }

    public Boolean payBefore(String optionsRadios, String amount, String ordersn, Member member) {
        MemberBalancePayLog paylog = new MemberBalancePayLog();
        switch (optionsRadios) {
            case "alipay":
                paylog.setPaymentCode(MemberBalancePayLog.PCALIPAY);
                paylog.setPaymentName(MemberBalancePayLog.PCALIPAY_NAME);
                break;
            case "unionpay":
                paylog.setPaymentCode(MemberBalancePayLog.PCUNIONPAY);
                paylog.setPaymentName(MemberBalancePayLog.PCUNIONPAY_NAME);
                break;
            case "weixin":
                paylog.setPaymentCode(MemberBalancePayLog.PCWXPAY);
                paylog.setPaymentName(MemberBalancePayLog.PCWXPAY_NAME);
                break;
            case "h5alipay":
                paylog.setPaymentCode(MemberBalancePayLog.H5ALIPAY);
                paylog.setPaymentName(MemberBalancePayLog.H5ALIPAY_NAME);
                break;
            case "h5unionpay":
                paylog.setPaymentCode(MemberBalancePayLog.H5UNIONPAY);
                paylog.setPaymentName(MemberBalancePayLog.H5UNIONPAY_NAME);
                break;
            case "h5weixin":
                paylog.setPaymentCode(MemberBalancePayLog.H5WXPAY);
                paylog.setPaymentName(MemberBalancePayLog.H5WXPAY_NAME);
                break;
            default:
                break;
        }
        paylog.setMemberId(member.getId());
        paylog.setMemberName(member.getName());
        paylog.setPayMoney(new BigDecimal(amount));
        paylog.setPayState(MemberBalancePayLog.UN_PAY);
        paylog.setPaySn(ordersn);
        paylog.setCreateTime(new Date());

        return memberBalancePayLogWriteDao.save(paylog) > 0;
    }

}