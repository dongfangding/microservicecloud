package com.ddf.microservicecloud.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author DDf on 2018/5/15
 * @EnableEurekaClient 表明当前模块是eureka的客户端
 */
@SpringBootApplication
@EnableEurekaClient
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
