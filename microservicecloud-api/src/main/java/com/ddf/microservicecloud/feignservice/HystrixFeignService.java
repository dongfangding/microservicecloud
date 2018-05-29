package com.ddf.microservicecloud.feignservice;

import com.ddf.microservicecloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
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
}
