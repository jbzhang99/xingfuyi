package com.yixiekeji.eureka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心
 *                       
 * @Filename: YiXieKeJiEureka.java
 * @Version: 1.0
 * @Author: 齐驱科技
 * @Email: wangpeng@yixiekeji.com
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class YiXieKeJiEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(YiXieKeJiEurekaApplication.class, args);
    }
}
