package com.yixiekeji.service.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberRule;

/**
 * 会员经验值和积分规则接口
 * 
 * @Filename: IMemberRuleService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberRule")
@Service(value = "memberRuleService")
public interface IMemberRuleService {

    /**
     * 根据id取得会员经验值和积分规则
     * @param  memberRuleId
     * @return
     */
    @RequestMapping(value = "getMemberRule", method = RequestMethod.GET)
    ServiceResult<MemberRule> getMemberRule(@RequestParam("memberRuleId") Integer memberRuleId);

    /**
    * 更新会员经验值和积分规则
    * @param  memberRule
    * @return
    */
    @RequestMapping(value = "updateMemberRule", method = RequestMethod.POST)
    ServiceResult<Integer> updateMemberRule(MemberRule memberRule);
}