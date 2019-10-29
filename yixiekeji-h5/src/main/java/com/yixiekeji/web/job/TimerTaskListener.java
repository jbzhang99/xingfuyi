package com.yixiekeji.web.job;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yixiekeji.core.EjavashopConfig;

/**
 * 
 * @author wangpeng
 * 功能：监听器，用来实现定时器
 */
@WebListener
public class TimerTaskListener extends HttpServlet implements ServletContextListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected static Logger   log              = LoggerFactory.getLogger(TimerTaskListener.class);

    private Timer             timer            = null;

    public TimerTaskListener() {
    }

    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        log.info("定时器销毁");
    }

    public void contextInitialized(ServletContextEvent event) {
        timer = new Timer(true);
        log.info("定时器已启动");
        timer.schedule(new IndexCacheTimerTask(event.getServletContext()),
            EjavashopConfig.TIME_DELAY, EjavashopConfig.TIME_PERIOD);
        log.info("已经添加任务调度表");
    }

}