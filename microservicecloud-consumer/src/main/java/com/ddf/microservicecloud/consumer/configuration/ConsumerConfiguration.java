package com.ddf.microservicecloud.consumer.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
     *
     * @Primary 因为配置了多个相同类型的RestTemplate的Bean，如果在注入Bean未明确指定名称，会报错，因此使用这个注解
     * 来表明这个是默认注入
     *
     * 默认注入的RestTemplate的connectionTimeOut和readTimeOut都是-1
     *
     * @return
     */
    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 对于某些外部系统的调用，如果不能因为长时间的错误挂起而影响主流程请求时间，可以定义超时时间
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate timeOutRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(30000);
        return new RestTemplate(requestFactory);
    }

    /**
     * 对于某些非Eureka服务的请求，可以去除@LoadBalanced注解
     * @return
     */
    @Bean
    public RestTemplate urlFactoryRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(30000);
        return new RestTemplate(requestFactory);
    }
}
