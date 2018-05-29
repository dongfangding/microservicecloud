package com.ddf.microservicecloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author DDf on 2018/5/15
 * @EnableEurekaClient 表明当前模块是eureka的客户端
 * @EnableFeignClients 启动Feign相关功能
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan("com")
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}
