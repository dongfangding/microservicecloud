package com.ddf.microservicecloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author DDf on 2018/5/14
 * @EnableEurekaClient 表明当前微服务是Eureka的客户端，将当前微服务注册到Eureka服务端中
 * @EnableCircuitBreaker 对Hystrix熔断的支持
 * @EnableCaching 开启对Spring缓存的支持
 * @EnableTransactionManagement 开启对事务的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableCaching
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
