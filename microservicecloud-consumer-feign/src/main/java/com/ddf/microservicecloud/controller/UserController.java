package com.ddf.microservicecloud.controller;

import com.ddf.microservicecloud.api.Api;
import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 调用微服务提供者的查询用户列表的接口
     * @return
     */
    @RequestMapping("/list")
    public List<User> userList() {
        return userService.queryAll();
    }

    @RequestMapping("/user/{id}")
    public User user(@PathVariable Integer id) {
        return userService.queryOne(id);
    }

    @RequestMapping("maplist")
    public List<Map<String, Object>> userMapList() {
        return userService.queryAllMap();
    }

}
