package com.yixiekeji.service.impl.member;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.MemberSignLogs;
import com.yixiekeji.model.member.MemberSignLogsModel;
import com.yixiekeji.service.member.IMemberSignLogsService;

@RestController
public class MemberSignLogsServiceImpl implements IMemberSignLogsService {
    private static Logger       log = LoggerFactory.getLogger(MemberSignLogsServiceImpl.class);

    @Resource
    private MemberSignLogsModel memberSignLogsModel;

    /**
     * 保存会员签到日志
     * @param  memberSignLogs
     * @return
     */
    @Override
    public ServiceResult<Boolean> saveMemberSignLogs(@RequestBody MemberSignLogs memberSignLogs) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(memberSignLogsModel.saveMemberSignLogs(memberSignLogs));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IMemberSignLogsService][saveMemberSignLogs]保存会员签到日志时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberSignLogsService][saveMemberSignLogs]保存会员签到日志时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> isMemberSignToday(@RequestParam("memberId") Integer memberId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(memberSignLogsModel.isMemberSignToday(memberId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IMemberSignLogsService][isMemberSignToday]判断会员当日是否签到时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberSignLogsService][isMemberSignToday]判断会员当日是否签到时出现未知异常：", e);
        }
        return result;
    }
}