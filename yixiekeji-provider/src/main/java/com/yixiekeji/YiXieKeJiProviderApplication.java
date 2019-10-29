package com.yixiekeji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class YiXieKeJiProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiXieKeJiProviderApplication.class, args);
    }

}
