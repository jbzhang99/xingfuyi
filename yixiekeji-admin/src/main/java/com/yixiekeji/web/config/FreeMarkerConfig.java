package com.yixiekeji.web.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.yixiekeji.web.shiro.tag.ShiroTags;
import com.yixiekeji.web.util.freemarker.EscapeHtmlTemplateLoader;
import com.yixiekeji.web.util.freemarker.FreemarkerStaticModels;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.TemplateModelException;

/**
 * Freemarker配置
 *                       
 * @Filename: FreemarkerView.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wangpeng@yixiekeji.com
 *
 */
@Configuration
public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private FreeMarkerConfigurer              freeMarkerConfigurer;

    @Autowired
    private FreeMarkerViewResolver            freeMarkerViewResolver;

    @Autowired
    private DomainUrlUtil                     domainUrlUtil;

    @Autowired
    private CodeManager                       codeManager;

    @PostConstruct
    public void setConfigure() throws Exception {
        configuration
            .setTemplateLoader(new EscapeHtmlTemplateLoader(configuration.getTemplateLoader()));

        configuration.setDefaultEncoding("UTF-8");
        configuration.setNumberFormat("#.####");
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        configuration.setLocale(new Locale("zh_CN"));
        configuration.setBooleanFormat("true,false");
        configuration.setWhitespaceStripping(true);

        configuration.setSharedVariable("domainUrlUtil", domainUrlUtil);
        configuration.setSharedVariable("codeManager", codeManager);
    }

    @PostConstruct
    public void freeMarkerConfigurer() {
        List<String> tlds = new ArrayList<>();
        tlds.add("/resources/tld/spring-form.tld");
        tlds.add("/resources/tld/spring.tld");
        TaglibFactory taglibFactory = freeMarkerConfigurer.getTaglibFactory();
        taglibFactory.setClasspathTlds(tlds);

        if (taglibFactory.getObjectWrapper() == null) {
            taglibFactory
                .setObjectWrapper(freeMarkerConfigurer.getConfiguration().getObjectWrapper());
        }
    }

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
        FreemarkerStaticModels freemarkerStaticModels = new FreemarkerStaticModels();
        freeMarkerViewResolver.setAttributesMap(freemarkerStaticModels);
    }

    @PostConstruct
    public void setStaticModels() {
        FreemarkerStaticModels freemarkerStaticModels = FreemarkerStaticModels.getInstance();
        //设置在模板中调用的名称，和要调用的Java类
        freemarkerStaticModels.setStaticModels("codeManager", CodeManager.class.getName());
        //        freemarkerStaticModels.setStaticModels("domainUrlUtil", DomainUrlUtil.class.getName());
        freeMarkerViewResolver.setAttributesMap(freemarkerStaticModels);
    }

}
