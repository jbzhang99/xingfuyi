package com.yixiekeji.service.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;

/**
 * 会员年度经验值递减service
 * 
 * @Filename: IMemberGradeDownLogsService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberGradeDownLogs")
@Service(value = "memberGradeDownLogsService")
public interface IMemberGradeDownLogsService {

    /**
     * 每天执行一次，对每年当天注册的会员减去相应的经验值数量，影响会员等级（处理完成后检查前2天的执行情况防止服务器维护等原因导致的定时任务未执行）
     * @return
     */
    @RequestMapping(value = "jobGradeValueDown", method = RequestMethod.GET)
    ServiceResult<Boolean> jobGradeValueDown();

}