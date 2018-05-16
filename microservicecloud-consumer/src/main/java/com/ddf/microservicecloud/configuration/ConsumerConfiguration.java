package com.ddf.microservicecloud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author DDf on 2018/5/15
 * 配置类
 */
@Configuration
public class ConsumerConfiguration {

    /**
     * @Bean表明这是要向spring上下文注册一个bean，bean的名称为方法名
     * RestTemplate为spring封装的一个专门用于Restful风格的客户端请求工具类
     *
     * @LoadBalanced 这个注解是基于ribbon的一个负载均衡的类，使用这个注解之后向服务端的请求则只能使用服务名，默认
     * 负载均衡的实现算法是轮循，即向这个服务名下所有可用的地址轮流发送请求
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
