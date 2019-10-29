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
import com.yixiekeji.entity.member.MemberRule;
import com.yixiekeji.model.member.MemberRuleModel;
import com.yixiekeji.service.member.IMemberRuleService;

@RestController
public class MemberRuleServiceImpl implements IMemberRuleService {
    private static Logger   log = LoggerFactory.getLogger(MemberRuleServiceImpl.class);

    @Resource
    private MemberRuleModel memberRuleModel;

    @Override
    public ServiceResult<MemberRule> getMemberRule(@RequestParam("memberRuleId") Integer memberRuleId) {
        ServiceResult<MemberRule> result = new ServiceResult<MemberRule>();
        try {
            result.setResult(memberRuleModel.getMemberRule(memberRuleId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberRuleService][getMemberRule]查询会员经验值和积分规则发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateMemberRule(@RequestBody MemberRule memberRule) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberRuleModel.updateMemberRule(memberRule));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberRuleService][updateMemberRule]更新会员经验值和积分规则发生异常:", e);
        }
        return result;
    }
}