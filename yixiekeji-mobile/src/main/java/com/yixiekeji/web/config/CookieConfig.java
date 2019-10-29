package com.yixiekeji.web.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cookie域名读取的问题
 *                       
 * @Filename: CookieConfig.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wangpeng@yixiekeji.com
 *
 */
@Configuration
public class CookieConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
            (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }

}