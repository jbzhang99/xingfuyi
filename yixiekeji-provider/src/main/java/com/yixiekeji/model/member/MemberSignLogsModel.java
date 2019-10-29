package com.yixiekeji.model.member;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.write.member.MemberGradeIntegralLogsWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberRuleWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberSignLogsWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberWriteDao;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberGradeIntegralLogs;
import com.yixiekeji.entity.member.MemberRule;
import com.yixiekeji.entity.member.MemberSignLogs;

@Component
public class MemberSignLogsModel {

    @Resource
    private MemberSignLogsWriteDao          memberSignLogsWriteDao;
    @Resource
    private MemberRuleWriteDao              memberRuleWriteDao;
    @Resource
    private DataSourceTransactionManager    transactionManager;
    @Resource
    private MemberWriteDao                  memberWriteDao;
    @Resource
    private MemberGradeIntegralLogsWriteDao memberGradeIntegralLogsWriteDao;

    /**
     * 获取当天签到信息
     * @param memberId
     * @return
     */
    private int getCountByMemberIdAndDate(Integer memberId) {
        // 获得服务器当前日期
        String day = TimeUtil.getToday();
        // 当天0点时间
        String start = day + " 00:00:00";
        // 当天24点时间
        String end = day + " 23:59:59";
        return memberSignLogsWriteDao.getCountByMemberIdAndDate(memberId, start, end);
    }

    /**
     * 保存会员签到日志
     * @param  memberSignLogs
     * @return
     */
    public boolean saveMemberSignLogs(MemberSignLogs memberSignLogs) {
        int signs = getCountByMemberIdAndDate(memberSignLogs.getMemberId());
        if (signs > 0) {
            throw new BusinessException("对不起，您今天已经签过到了，谢谢。");
        }
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            MemberRule memberRule = memberRuleWriteDao.get(ConstantsEJS.MEMBER_RULE_INTEGRAL_ID, 1);
            // 会员积分规则不为空并且签到积分大于0
            if (memberRule != null && memberRule.getSign() != null && memberRule.getSign() > 0) {
                // 修改用户积分数，记录积分消耗日志
                Member memberNew = new Member();
                memberNew.setId(memberSignLogs.getMemberId());
                memberNew.setIntegral(memberRule.getSign());
                Integer updateIntegral = memberWriteDao.updateIntegral(memberNew);
                if (updateIntegral == 0) {
                    throw new BusinessException("用户签到送积分时失败，请重试！");
                }
                MemberGradeIntegralLogs memberGradeIntegralLogs = new MemberGradeIntegralLogs();
                memberGradeIntegralLogs.setMemberId(memberSignLogs.getMemberId());
                memberGradeIntegralLogs.setMemberName(memberSignLogs.getMemberName());
                memberGradeIntegralLogs.setValue(memberRule.getSign());
                memberGradeIntegralLogs
                    .setOptType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_10);
                memberGradeIntegralLogs.setOptDes("签到送积分");
                memberGradeIntegralLogs.setType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
                memberGradeIntegralLogs.setCreateTime(new Date());
                Integer save = memberGradeIntegralLogsWriteDao.save(memberGradeIntegralLogs);
                if (save == 0) {
                    throw new BusinessException("记录用户积分消费日志失败，请重试！");
                }
            }

            Integer insert = memberSignLogsWriteDao.insert(memberSignLogs);
            if (insert == 0) {
                throw new BusinessException("记录用户签到日志失败，请重试！");
            }
            transactionManager.commit(status);
            return insert > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    /**
    * 会员今日是否签到
    * @param  memberId
    * @return true：当日已签到；false：当日未签到
    */
    public boolean isMemberSignToday(Integer memberId) {
        int signs = getCountByMemberIdAndDate(memberId);
        if (signs > 0) {
            return true;
        }
        return false;
    }

}