package com.yixiekeji.web.job;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.service.member.IMobileVerifyCodeService;

@Component
public class MobileJob {
    private static Logger            log = LoggerFactory.getLogger(MobileJob.class);

    @Resource
    private IMobileVerifyCodeService mobileVerifyCodeService;

    /**
     * 系统定时任务清除1天之前添加的短信数据
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void jobClearSmsCode() {
        log.info("jobClearSmsCode() start");
        ServiceResult<Boolean> jobResult = mobileVerifyCodeService.jobClearSmsCode();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-mobile][MobileJob][jobClearSmsCode] 系统定时任务清除1天之前添加的短信数据时失败："
                      + jobResult.getMessage());
        }
        log.info("jobClearSmsCode() end");
    }

}
