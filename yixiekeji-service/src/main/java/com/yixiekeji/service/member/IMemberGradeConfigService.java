package com.yixiekeji.service.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberGradeConfig;

/**
 * 会员等级配置表接口
 * 
 * @Filename: IMemberGradeConfigService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberGradeConfig")
@Service(value = "memberGradeConfigService")
public interface IMemberGradeConfigService {

    /**
     * 根据id取得会员等级配置表
     * @param  memberGradeConfigId
     * @return
     */
    @RequestMapping(value = "getMemberGradeConfig", method = RequestMethod.GET)
    ServiceResult<MemberGradeConfig> getMemberGradeConfig(@RequestParam("memberGradeConfigId") Integer memberGradeConfigId);

    /**
    * 更新会员等级配置表
    * @param  memberGradeConfig
    * @return
    */
    @RequestMapping(value = "updateMemberGradeConfig", method = RequestMethod.POST)
    ServiceResult<Integer> updateMemberGradeConfig(MemberGradeConfig memberGradeConfig);
}