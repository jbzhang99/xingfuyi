package com.yixiekeji.model.member;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.member.MemberReadDao;
import com.yixiekeji.dao.shop.write.member.MemberGradeConfigWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberGradeDownLogsWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberGradeIntegralLogsWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberGradeUpLogsWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberWriteDao;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberGradeConfig;
import com.yixiekeji.entity.member.MemberGradeDownLogs;
import com.yixiekeji.entity.member.MemberGradeIntegralLogs;
import com.yixiekeji.entity.member.MemberGradeUpLogs;

@Component
public class MemberGradeDownLogsModel {

    private static org.slf4j.Logger         log  = org.slf4j.LoggerFactory
        .getLogger(MemberGradeDownLogsModel.class);

    @Resource
    private MemberGradeDownLogsWriteDao     memberGradeDownLogsWriteDao;
    @Resource
    private DataSourceTransactionManager    transactionManager;
    @Resource
    private MemberReadDao                   memberReadDao;
    @Resource
    private MemberWriteDao                  memberWriteDao;
    @Resource
    private MemberGradeIntegralLogsWriteDao memberGradeIntegralLogsWriteDao;
    @Resource
    private MemberGradeUpLogsWriteDao       memberGradeUpLogsWriteDao;
    @Resource
    private MemberGradeConfigWriteDao       memberGradeConfigWriteDao;

    /**
     * 每次查询数量
     */
    private static final int                SIZE = 200;

    /**
     * 每天执行一次，对每年当天注册的会员减去相应的经验值数量，影响会员等级（处理完成后检查前2天的执行情况防止服务器维护等原因导致的定时任务未执行）
     * @return
     */
    public boolean jobGradeValueDown() {
        // 1.1 获得服务器当前日期
        String day = TimeUtil.getToday();
        // 1.2 执行当天的任务
        this.doGradeValueDown(day);

        // 2.1 获得服务器当前日期前一天的日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        day = TimeUtil.getStrByCalendar(calendar);
        // 2.2 执行前一天的任务
        this.doGradeValueDown(day);

        // 3.1 获得服务器当前日期前二天的日期
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        day = TimeUtil.getStrByCalendar(calendar);
        // 3.2 执行前二天的任务
        this.doGradeValueDown(day);

        // 4 处理2月29日注册的情形
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if ((year % 4) > 0) {
            // 非闰年则特殊处理
            // 在3月1日和3月2日和3月3日三天检查2月29日注册会员的减少情况
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            if (month == 2 && (dayOfMonth == 1 || dayOfMonth == 2 || dayOfMonth == 3)) {
                this.doGradeValueDown(year + "-02-29");
                log.info("----------jobGradeValueDown（）非闰年执行2月29日注册的用户减少经验值");
            }
        }
        return true;
    }

    /**
     * 执行某一天的经验值递减
     * @param day 形式必须为：yyyy-MM-dd
     */
    private void doGradeValueDown(String day) {
        log.info("------------doGradeValueDown()开始：" + day + "-----------");
        List<MemberGradeDownLogs> logs = memberGradeDownLogsWriteDao.getByExcuteTime(day);
        if (logs != null && logs.size() > 0) {
            // 如果当天已经执行过了则不再执行，直接返回
            log.info("------------doGradeValueDown()结束（当天已经执行）：" + day + "-----------");
            return;
        }

        // 会员等级配置表等级经验值
        MemberGradeConfig memberGradeConfig_1 = memberGradeConfigWriteDao
            .get(ConstantsEJS.MEMBER_GRADE_CONFIG_ID);
        if (memberGradeConfig_1 == null) {
            log.info("------------doGradeValueDown()结束（会员等级配置表等级经验值为空）：" + day + "-----------");
            return;
        }

        // 会员等级配置表年度减少经验值
        MemberGradeConfig memberGradeConfig_2 = memberGradeConfigWriteDao
            .get(ConstantsEJS.MEMBER_GRADE_CONFIG_DOWN_ID);
        if (memberGradeConfig_2 == null) {
            log.info("------------doGradeValueDown()结束（会员等级配置表年度减少经验值为空）：" + day + "-----------");
            return;
        }

        // 减少值都是0就不执行后续逻辑
        if (memberGradeConfig_2.getGrade1() <= 0 && memberGradeConfig_2.getGrade2() <= 0
            && memberGradeConfig_2.getGrade3() <= 0 && memberGradeConfig_2.getGrade4() <= 0
            && memberGradeConfig_2.getGrade5() <= 0) {
            log.info("------------doGradeValueDown()结束（所有减少值为0）：" + day + "-----------");
            return;
        }

        // 第一次查询size条，循环执行
        int startIndex = 0;
        List<Member> list = memberReadDao.getMembersByDay(day.substring(4), startIndex, SIZE);

        while (list != null && list.size() > 0) {
            for (Member member : list) {
                // 会员旧等级，等级匹配，减少相应的经验值
                Integer oldGrd = member.getGrade();
                // 新的等级，初始化跟旧等级一致
                Integer newGrd = oldGrd;
                // 要减少的经验值
                int gradeValueDown = 0;
                // 等级不为空且等级大于0且会员经验值大于0
                if (oldGrd != null && oldGrd > 0 && member.getGradeValue() > 0) {
                    // 事务管理，在循环内，没执行一条提交一次，一条失败不影响其他数据执行
                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                    TransactionStatus status = transactionManager.getTransaction(def);
                    try {

                        if (oldGrd.intValue() == Member.GRADE_1) {
                            // 注册会员
                            gradeValueDown = memberGradeConfig_2.getGrade1();
                        } else if (oldGrd.intValue() == Member.GRADE_2) {
                            // 铜牌会员
                            gradeValueDown = memberGradeConfig_2.getGrade2();
                        } else if (oldGrd.intValue() == Member.GRADE_3) {
                            // 银牌会员
                            gradeValueDown = memberGradeConfig_2.getGrade3();
                        } else if (oldGrd.intValue() == Member.GRADE_4) {
                            // 金牌会员
                            gradeValueDown = memberGradeConfig_2.getGrade4();
                        } else if (oldGrd.intValue() == Member.GRADE_5) {
                            // 钻石会员
                            gradeValueDown = memberGradeConfig_2.getGrade5();
                        }

                        // 如果会员的经验值不够减少的经验值则减少会员现有的经验值
                        int oldGrdVal = member.getGradeValue();
                        if (gradeValueDown > oldGrdVal) {
                            gradeValueDown = oldGrdVal;
                        }

                        // 判断会员的等级
                        // 新的经验值
                        Integer newGrdVal = oldGrdVal - gradeValueDown;

                        // 取得会员等级配置，判断会员等级
                        if (newGrdVal >= memberGradeConfig_1.getGrade5()) {
                            newGrd = Member.GRADE_5;
                        } else if (newGrdVal >= memberGradeConfig_1.getGrade4()) {
                            newGrd = Member.GRADE_4;
                        } else if (newGrdVal >= memberGradeConfig_1.getGrade3()) {
                            newGrd = Member.GRADE_3;
                        } else if (newGrdVal >= memberGradeConfig_1.getGrade2()) {
                            newGrd = Member.GRADE_2;
                        } else {
                            newGrd = Member.GRADE_1;
                        }
                        // 如果等级有变化，记录等级变化日志表
                        if (!newGrd.equals(oldGrd)) {
                            // 会员等级升级日志表
                            MemberGradeUpLogs upLogs = new MemberGradeUpLogs();
                            upLogs.setMemberId(member.getId());
                            upLogs.setMemberName(member.getName());
                            upLogs.setBeforeExper(oldGrdVal);
                            upLogs.setAfterExper(newGrdVal);
                            upLogs.setBeforeGrade(oldGrd);
                            upLogs.setAfterGrade(newGrd);
                            upLogs.setCreateTime(new Date());
                            // 记录日志
                            int upLog = memberGradeUpLogsWriteDao.save(upLogs);
                            if (upLog == 0) {
                                throw new BusinessException("会员等级升级日志记录失败，请重试！");
                            }
                        }
                        // 更新会员经验值、等级
                        Member newMember = new Member();
                        newMember.setId(member.getId());
                        newMember.setGrade(newGrd);
                        // 减少值，设定为负数
                        newMember.setGradeValue(0 - gradeValueDown);
                        Integer updateGrade = memberWriteDao.updateGrade(newMember);
                        if (updateGrade == 0) {
                            throw new BusinessException("更新会员经验值及等级失败，请重试！");
                        }

                        // 记录会员经验值日志
                        MemberGradeIntegralLogs memberGradeIntegralLogs = new MemberGradeIntegralLogs();
                        memberGradeIntegralLogs.setMemberId(member.getId());
                        memberGradeIntegralLogs.setMemberName(member.getName());
                        memberGradeIntegralLogs.setValue(gradeValueDown);
                        memberGradeIntegralLogs
                            .setOptType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_9);
                        memberGradeIntegralLogs.setOptDes("会员经验值年度减少");
                        memberGradeIntegralLogs
                            .setType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_1);
                        memberGradeIntegralLogs.setCreateTime(new Date());
                        Integer save = memberGradeIntegralLogsWriteDao
                            .save(memberGradeIntegralLogs);
                        if (save == 0) {
                            throw new BusinessException("记录用户经验值日志失败，请重试！");
                        }

                        transactionManager.commit(status);
                    } catch (Exception e) {
                        transactionManager.rollback(status);
                        log.error(e.getMessage(), e);
                    }
                }
            }

            // 每完成一次查询下一次的数据
            startIndex += SIZE;
            list = memberReadDao.getMembersByDay(day.substring(4), startIndex, SIZE);
        }

        // 保存执行日志
        MemberGradeDownLogs memberGradeDownLogs = new MemberGradeDownLogs();
        memberGradeDownLogs.setExcuteTime(day);
        memberGradeDownLogs.setCreateTime(new Date());
        memberGradeDownLogsWriteDao.insert(memberGradeDownLogs);

        log.info("------------doGradeValueDown()结束：" + day + "-----------");
    }

}