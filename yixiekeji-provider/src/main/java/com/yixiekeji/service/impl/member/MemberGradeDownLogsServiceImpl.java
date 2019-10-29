package com.yixiekeji.service.impl.member;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.model.member.MemberGradeDownLogsModel;
import com.yixiekeji.service.member.IMemberGradeDownLogsService;

@RestController
public class MemberGradeDownLogsServiceImpl implements IMemberGradeDownLogsService {
    private static Logger            log = LoggerFactory
        .getLogger(MemberGradeDownLogsServiceImpl.class);

    @Resource
    private MemberGradeDownLogsModel memberGradeDownLogsModel;

    @Override
    public ServiceResult<Boolean> jobGradeValueDown() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(memberGradeDownLogsModel.jobGradeValueDown());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberGradeDownLogsService][jobGradeValueDown]对每年当天注册的会员减去相应的经验值数量时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IMemberGradeDownLogsService][jobGradeValueDown]对每年当天注册的会员减去相应的经验值数量时出现未知异常：", e);
        }
        return result;
    }

}