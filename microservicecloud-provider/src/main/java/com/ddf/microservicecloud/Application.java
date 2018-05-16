package com.ddf.microservicecloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author DDf on 2018/5/14
 * @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        System.out.println("--------------------");
        SpringApplication.run(Application.class, args);
    }
}
