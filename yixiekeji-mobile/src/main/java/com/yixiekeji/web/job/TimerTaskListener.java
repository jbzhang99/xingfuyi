package com.yixiekeji.web.job;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yixiekeji.core.EjavashopConfig;

/**
 * 
 * @author wangpeng
 * 功能：监听器，用来实现定时器
 */
public class TimerTaskListener extends HttpServlet implements ServletContextListener {

    /**
     * 
     */
    private static final long serialVersionUID   = 1L;

    private static Logger     log                = LoggerFactory.getLogger(TimerTaskListener.class);

    private Timer             timer              = null;
    private Timer             WxAccessTokenTimer = null;

    public TimerTaskListener() {
    }

    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        log.info("定时器销毁");
    }

    public void contextInitialized(ServletContextEvent event) {
        timer = new Timer(true);
        //微信调度
        WxAccessTokenTimer = new Timer(false);

        log.info("定时器已启动");
        timer.schedule(new IndexCacheTimerTask(event.getServletContext()),
            EjavashopConfig.TIME_DELAY, EjavashopConfig.TIME_PERIOD);

        //微信调度
        timer.schedule(new GetWxAccessTokenTimerTask(event.getServletContext()),
            EjavashopConfig.ACCESS_TOKEN_DELAY, EjavashopConfig.ACCESS_TOKEN_PERIOD);

        log.info("已经添加任务调度表");
    }

}
