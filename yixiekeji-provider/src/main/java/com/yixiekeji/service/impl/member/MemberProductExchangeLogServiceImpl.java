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
import com.yixiekeji.entity.member.MemberProductExchangeLog;
import com.yixiekeji.model.member.MemberProductExchangeLogModel;
import com.yixiekeji.service.member.IMemberProductExchangeLogService;

@RestController
public class MemberProductExchangeLogServiceImpl implements IMemberProductExchangeLogService {
    private static Logger                 log = LoggerFactory
        .getLogger(MemberProductExchangeLogServiceImpl.class);

    @Resource
    private MemberProductExchangeLogModel memberProductExchangeLogModel;

    /**
    * 根据id取得换货日志表
    * @param  memberProductExchangeLogId
    * @return
    */
    @Override
    public ServiceResult<MemberProductExchangeLog> getMemberProductExchangeLogById(@RequestParam("memberProductExchangeLogId") Integer memberProductExchangeLogId) {
        ServiceResult<MemberProductExchangeLog> result = new ServiceResult<MemberProductExchangeLog>();
        try {
            result.setResult(memberProductExchangeLogModel
                .getMemberProductExchangeLogById(memberProductExchangeLogId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberProductExchangeLogService][getMemberProductExchangeLogById]根据id["
                      + memberProductExchangeLogId + "]取得换货日志表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberProductExchangeLogService][getMemberProductExchangeLogById]根据id["
                      + memberProductExchangeLogId + "]取得换货日志表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存换货日志表
     * @param  memberProductExchangeLog
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMemberProductExchangeLog(@RequestBody MemberProductExchangeLog memberProductExchangeLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberProductExchangeLogModel
                .saveMemberProductExchangeLog(memberProductExchangeLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IMemberProductExchangeLogService][saveMemberProductExchangeLog]保存换货日志表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IMemberProductExchangeLogService][saveMemberProductExchangeLog]保存换货日志表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
    * 更新换货日志表
    * @param  memberProductExchangeLog
    * @return
    * @see com.yixiekeji.service.MemberProductExchangeLogService#updateMemberProductExchangeLog(MemberProductExchangeLog)
    */
    @Override
    public ServiceResult<Integer> updateMemberProductExchangeLog(@RequestBody MemberProductExchangeLog memberProductExchangeLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberProductExchangeLogModel
                .updateMemberProductExchangeLog(memberProductExchangeLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IMemberProductExchangeLogService][updateMemberProductExchangeLog]更新换货日志表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IMemberProductExchangeLogService][updateMemberProductExchangeLog]更新换货日志表时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MemberProductExchangeLog>> getMemberProductExchangeLogByMemberProductExchangeId(@RequestParam("memberProductExchangeId") Integer memberProductExchangeId) {
        ServiceResult<List<MemberProductExchangeLog>> result = new ServiceResult<List<MemberProductExchangeLog>>();
        try {
            result.setResult(memberProductExchangeLogModel
                .getMemberProductExchangeLogByMemberProductExchangeId(memberProductExchangeId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IMemberProductExchangeLogService][getMemberProductExchangeLogByMemberProductExchangeId]根据id["
                      + memberProductExchangeId + "]取得换货日志表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IMemberProductExchangeLogService][getMemberProductExchangeLogByMemberProductExchangeId]根据id["
                      + memberProductExchangeId + "]取得换货日志表时出现未知异常：",
                e);
        }
        return result;
    }
}