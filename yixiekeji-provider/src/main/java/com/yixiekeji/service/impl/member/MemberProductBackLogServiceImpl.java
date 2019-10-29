package com.yixiekeji.service.impl.member;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.MemberProductBackLog;
import com.yixiekeji.model.member.MemberProductBackLogModel;
import com.yixiekeji.service.member.IMemberProductBackLogService;

@RestController
public class MemberProductBackLogServiceImpl implements IMemberProductBackLogService {
    private static Logger             log = LoggerFactory
        .getLogger(MemberProductBackLogServiceImpl.class);

    @Resource
    private MemberProductBackLogModel memberProductBackLogModel;

    /**
    * 根据id取得退货日志表
    * @param  memberProductBackLogId
    * @return
    */
    @Override
    public ServiceResult<MemberProductBackLog> getMemberProductBackLogById(@RequestParam("memberProductBackLogId") Integer memberProductBackLogId) {
        ServiceResult<MemberProductBackLog> result = new ServiceResult<MemberProductBackLog>();
        try {
            result.setResult(
                memberProductBackLogModel.getMemberProductBackLogById(memberProductBackLogId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberProductBackLogService][getMemberProductBackLogById]根据id["
                      + memberProductBackLogId + "]取得退货日志表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberProductBackLogService][getMemberProductBackLogById]根据id["
                      + memberProductBackLogId + "]取得退货日志表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存退货日志表
     * @param  memberProductBackLog
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMemberProductBackLog(@RequestBody MemberProductBackLog memberProductBackLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                memberProductBackLogModel.saveMemberProductBackLog(memberProductBackLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberProductBackLogService][saveMemberProductBackLog]保存退货日志表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberProductBackLogService][saveMemberProductBackLog]保存退货日志表时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新退货日志表
    * @param  memberProductBackLog
    * @return
    * @see com.yixiekeji.service.MemberProductBackLogService#updateMemberProductBackLog(MemberProductBackLog)
    */
    @Override
    public ServiceResult<Integer> updateMemberProductBackLog(@RequestBody MemberProductBackLog memberProductBackLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                memberProductBackLogModel.updateMemberProductBackLog(memberProductBackLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberProductBackLogService][updateMemberProductBackLog]更新退货日志表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberProductBackLogService][updateMemberProductBackLog]更新退货日志表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MemberProductBackLog>> getMemberProductBackLogByMemberProductBackId(@RequestParam("memberProductBackLogId") Integer memberProductBackLogId) {
        ServiceResult<List<MemberProductBackLog>> result = new ServiceResult<List<MemberProductBackLog>>();
        try {
            result.setResult(memberProductBackLogModel
                .getMemberProductBackLogByMemberProductBackId(memberProductBackLogId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IMemberProductBackLogService][getMemberProductBackLogByMemberProductBackId]根据id["
                      + memberProductBackLogId + "]取得退货日志表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IMemberProductBackLogService][getMemberProductBackLogByMemberProductBackId]根据id["
                      + memberProductBackLogId + "]取得退货日志表时出现未知异常：",
                e);
        }
        return result;
    }
}