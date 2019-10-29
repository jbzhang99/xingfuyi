package com.yixiekeji.web;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.yixiekeji.web.config.FreemarkerView;
import com.yixiekeji.web.csrf.CsrfRequestDataValueProcessor;
import com.yixiekeji.web.util.freemarker.InitJSCodeContainerTemplateMethodModel;

import freemarker.template.utility.XmlEscape;
import javax.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients(basePackages = { "com.yixiekeji.service" })
public class YiXieKeJiSellerApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiXieKeJiSellerApplication.class, args);
    }

    @Bean
    public CsrfRequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

    @Bean
    public CommandLineRunner customFreemarker(FreeMarkerViewResolver resolver) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                //增加视图
                resolver.setViewClass(FreemarkerView.class);
                //添加自定义解析器
                Map<String, Object> map = resolver.getAttributesMap();
                map.put("xml_escape", new XmlEscape());
                map.put("initJSCodeContainer", new InitJSCodeContainerTemplateMethodModel());
            }
        };
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("102400KB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }

}
