package com.yixiekeji.web.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * Freemarker视图配置
 *                       
 * @Filename: FreemarkerView.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wangpeng@yixiekeji.com
 *
 */
public class FreemarkerView extends FreeMarkerView {

    @Override
    protected void exposeHelpers(Map<String, Object> model,
                                 HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
    }

}
