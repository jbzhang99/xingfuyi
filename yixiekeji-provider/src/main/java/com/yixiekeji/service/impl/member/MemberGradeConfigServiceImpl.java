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
import com.yixiekeji.entity.member.MemberGradeConfig;
import com.yixiekeji.model.member.MemberGradeConfigModel;
import com.yixiekeji.service.member.IMemberGradeConfigService;

@RestController
public class MemberGradeConfigServiceImpl implements IMemberGradeConfigService {
    private static Logger          log = LoggerFactory
        .getLogger(MemberGradeConfigServiceImpl.class);

    @Resource
    private MemberGradeConfigModel memberGradeConfigModel;

    @Override
    public ServiceResult<MemberGradeConfig> getMemberGradeConfig(@RequestParam("memberGradeConfigId") Integer memberGradeConfigId) {
        ServiceResult<MemberGradeConfig> result = new ServiceResult<MemberGradeConfig>();
        try {
            result.setResult(memberGradeConfigModel.getMemberGradeConfig(memberGradeConfigId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberGradeConfigService][getMemberGradeConfig]查询会员等级配置表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateMemberGradeConfig(@RequestBody MemberGradeConfig memberGradeConfig) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberGradeConfigModel.updateMemberGradeConfig(memberGradeConfig));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberGradeConfigService][updateMemberGradeConfig]更新会员等级配置表时发生异常:", e);
        }
        return result;
    }
}