package com.ddf.microservicecloud.service;

import com.ddf.microservicecloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


/**
 * @author DDf on 2018/5/14
 */
@FeignClient(value="microservicecloud-provider")
public interface UserService {

    @RequestMapping("/list")
    List<User> queryAll();

    @RequestMapping("/user/{id}")
    User queryOne(Integer id);

    @RequestMapping("maplist")
    List<Map<String, Object>> queryAllMap();
}
