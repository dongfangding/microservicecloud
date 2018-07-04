package com.ddf.microservicecloud.feign.feignservice;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.api.feignservice.HystrixFallFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author DDf on 2018/5/29
 */
@FeignClient(value="MICROSERVICECLOUD-PROVIDER", fallbackFactory = HystrixFallFactory.class)
public interface HystrixFeignService {
    /**
     * 这里的RequestMapping要写成当前FeignService要访问的微服务名中定义的对应接口的访问路径，
     * /provider为MICROSERVICECLOUD-PROVIDER的context-path，暂时未找到在一个地方统一定义
     * @return
     */
    @RequestMapping("/provider/hystrix/hystrixList")
    List<User> hystrixList();

    @RequestMapping("/provider/user/list")
    List<User> userList();


    /**
     * 这里的@PathVariable一定要写value，否则会报错
     * @param id
     * @return
     */
    @RequestMapping("provider/hystrix/queryOne/{id}")
    User queryOne(@PathVariable(value = "id") Integer id);
}
