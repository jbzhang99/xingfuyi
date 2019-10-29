package com.yixiekeji.web.job;

import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yixiekeji.core.ConstantsEJS;
import com.wxpay.util.HttpUtil;

/**
 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
 * 开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。
 * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 *                       
 * @Filename: GetWxAccessTokenTimerTask.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public class GetWxAccessTokenTimerTask extends TimerTask {

    private static final Log log = LogFactory.getLog(IndexCacheTimerTask.class);

    private ServletContext context   = null;
    private static boolean isRunning = false;

    public GetWxAccessTokenTimerTask() {
    }

    public GetWxAccessTokenTimerTask(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        log.info("----------------定时器开始执行,执行时间：" + new Date());
        if (!isRunning) {
            isRunning = true;
            String accessToken;
            try {
                accessToken = HttpUtil.getAccessToken();
                context.setAttribute(ConstantsEJS.WX_ACCESS_TOKEN, accessToken);
                log.info("刷新access-token:" + accessToken);
                isRunning = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("上次还在执行中……");
        }
        log.info("----------------定时器执行结束-----------------------");
    }

}
