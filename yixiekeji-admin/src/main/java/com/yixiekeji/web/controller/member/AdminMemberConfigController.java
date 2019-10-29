package com.yixiekeji.web.controller.member;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.member.MemberGradeConfig;
import com.yixiekeji.entity.member.MemberRule;
import com.yixiekeji.service.member.IMemberGradeConfigService;
import com.yixiekeji.service.member.IMemberRuleService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 会员规则配置controller
 *
 * @Filename: AdminMemberConfigController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/member/config", produces = "application/json;charset=UTF-8")
public class AdminMemberConfigController extends BaseController {

    @Resource
    private IMemberRuleService        memberRuleService;

    @Resource
    private IMemberGradeConfigService memberGradeConfigService;

    /**
     * 会员经验值规则编辑初始化页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "gradevalue", method = { RequestMethod.GET })
    public String gradeValueIndex(Map<String, Object> dataMap) throws Exception {
        ServiceResult<MemberRule> result = memberRuleService
            .getMemberRule(ConstantsEJS.MEMBER_RULE_GRADE_ID);
        dataMap.put("memberRule", result.getResult());
        return "admin/member/config/gradevalueconfigedit";
    }

    /**
     * 会员积分值规则编辑初始化页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "intvalue", method = { RequestMethod.GET })
    public String intValueIndex(Map<String, Object> dataMap) throws Exception {
        ServiceResult<MemberRule> result = memberRuleService
            .getMemberRule(ConstantsEJS.MEMBER_RULE_INTEGRAL_ID);
        dataMap.put("memberRule", result.getResult());
        return "admin/member/config/intvalueconfigedit";
    }

    /**
     * 会员经验值规则编辑页面确定按钮，更新规则
     * @param memberRule
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "gradevalue/update", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> gradeUpdateMemberRule(MemberRule memberRule,
                                                                       HttpServletRequest request,
                                                                       Map<String, Object> dataMap) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Integer> serviceResult = memberRuleService.updateMemberRule(memberRule);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("memberRule", memberRule);
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        jsonResult.setData(true);
        return jsonResult;
    }

    /**
     * 会员经验值和积分规则编辑页面确定按钮，更新规则
     * @param memberRule
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "intvalue/update", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> intUpdateMemberRule(MemberRule memberRule,
                                                                     HttpServletRequest request,
                                                                     Map<String, Object> dataMap) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Integer> serviceResult = memberRuleService.updateMemberRule(memberRule);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("memberRule", memberRule);
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        jsonResult.setData(true);
        return jsonResult;
    }

    /**
     * 会员等级配置页面初始化
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "grade", method = { RequestMethod.GET })
    public String gradeIndex(Map<String, Object> dataMap) throws Exception {
        ServiceResult<MemberGradeConfig> result = memberGradeConfigService
            .getMemberGradeConfig(ConstantsEJS.MEMBER_GRADE_CONFIG_ID);
        dataMap.put("memberGradeConfig", result.getResult());
        return "admin/member/config/gradeconfigedit";
    }

    /**
     * 会员等级配置页面确定按钮，更新会员等级配置表
     * @param memberRule
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "grade/update", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> updateMemberGradeConfig(MemberGradeConfig memberGradeConfig,
                                                                         HttpServletRequest request,
                                                                         Map<String, Object> dataMap) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Integer> serviceResult = memberGradeConfigService
            .updateMemberGradeConfig(memberGradeConfig);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("memberGradeConfig", memberGradeConfig);
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        jsonResult.setData(true);
        return jsonResult;
    }

    /**
     * 会员等级配置（年度减少经验值）页面初始化
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "gradedown", method = { RequestMethod.GET })
    public String gradeDownIndex(Map<String, Object> dataMap) throws Exception {
        ServiceResult<MemberGradeConfig> result = memberGradeConfigService
            .getMemberGradeConfig(ConstantsEJS.MEMBER_GRADE_CONFIG_DOWN_ID);
        dataMap.put("memberGradeConfig", result.getResult());
        return "admin/member/config/gradeconfigdownedit";
    }

    /**
     * 会员等级配置（年度减少经验值）页面确定按钮，更新会员等级配置表
     * @param memberRule
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "gradedown/update", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> updateMemberGradeConfigDown(MemberGradeConfig memberGradeConfig,
                                                                             HttpServletRequest request,
                                                                             Map<String, Object> dataMap) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Integer> serviceResult = memberGradeConfigService
            .updateMemberGradeConfig(memberGradeConfig);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("memberGradeConfig", memberGradeConfig);
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        jsonResult.setData(true);
        return jsonResult;
    }
}
