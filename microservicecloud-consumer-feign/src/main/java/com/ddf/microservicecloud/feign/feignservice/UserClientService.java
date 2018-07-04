package com.ddf.microservicecloud.feign.feignservice;

import com.ddf.microservicecloud.api.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author DDf on 2018/5/28
 * @FeignClient 指定当前接口需要调用的微服务名
 */
@FeignClient(value="MICROSERVICECLOUD-PROVIDER")
public interface UserClientService {
    /**
     * 这里的RequestMapping要写成当前FeignClient要访问的微服务名中定义的对应接口的访问路径，
     * /provider为MICROSERVICECLOUD-PROVIDER的context-path，暂时未找到在一个地方统一定义
     * @return
     */
    @RequestMapping("/provider/user/list")
    List<User> queryAll();

    @RequestMapping("/provider/user/user/{id}")
    User queryOne(@PathVariable("id") Integer id);
}
