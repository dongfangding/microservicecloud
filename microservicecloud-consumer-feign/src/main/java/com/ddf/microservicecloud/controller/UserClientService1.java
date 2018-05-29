package com.ddf.microservicecloud.controller;

import com.ddf.microservicecloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/28
 */
@FeignClient(value="MICROSERVICECLOUD-PROVIDER")
public interface UserClientService1 {
    @RequestMapping("/provider/user/list")
    List<User> queryAll();

    @RequestMapping("/provideruser/{id}")
    User queryOne(@PathVariable("id") Integer id);

    @RequestMapping("/provider/user/maplist")
    List<Map<String, Object>> queryAllMap();
}
