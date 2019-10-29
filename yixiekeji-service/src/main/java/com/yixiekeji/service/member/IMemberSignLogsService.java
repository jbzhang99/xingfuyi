package com.yixiekeji.service.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberSignLogs;

/**
 * 会员签到service
 *
 * @Filename: IMemberSignLogsService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberSignLogs")
@Service(value = "memberSignLogsService")
public interface IMemberSignLogsService {

    /**
     * 保存会员签到日志
     * @param  memberSignLogs
     * @return
     */
    @RequestMapping(value = "saveMemberSignLogs", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMemberSignLogs(MemberSignLogs memberSignLogs);

    /**
     * 会员当日是否签到
     * @param  memberId
     * @return true：当日已签到；false：当日未签到
     */
    @RequestMapping(value = "isMemberSignToday", method = RequestMethod.GET)
    ServiceResult<Boolean> isMemberSignToday(@RequestParam("memberId") Integer memberId);

}