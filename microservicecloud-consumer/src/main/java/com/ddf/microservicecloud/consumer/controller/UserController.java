package com.ddf.microservicecloud.consumer.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.consumer.api.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author DDf on 2018/5/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Api api;

    /**
     * 调用微服务提供者的查询用户列表的接口
     * @return
     */
    @RequestMapping("/list")
    public List<User> userList() {
        List<User> userList = restTemplate.postForObject(api.getProviderUser() + "/list", null, List.class);
        return userList;
    }
}
