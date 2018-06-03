package com.ddf.microservicecloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author DDf on 2018/5/14
 * @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableAsync
@EnableScheduling
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
