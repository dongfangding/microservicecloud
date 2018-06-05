package com.ddf.microservicecloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author DDf on 2018/5/15
 * @EnableEurekaClient 表明当前模块是eureka的客户端
 * @EnableFeignClients 启动Feign相关功能
 * @EnableScheduling 启动对定时任务的支持
 * @EnableAsync 开启对异步调用的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan("com.ddf.microservicecloud")
@EnableScheduling
@EnableAsync
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}
