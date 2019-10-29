package com.yixiekeji.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.yixiekeji.web.csrf.CsrfRequestDataValueProcessor;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@ServletComponentScan
@EnableFeignClients(basePackages = { "com.yixiekeji.service" })
public class YiXieKeJiMobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiXieKeJiMobileApplication.class, args);
    }

    @Bean
    public CsrfRequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

}
