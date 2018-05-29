package com.ddf.microservicecloud.controller;

import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.feignservice.UserClientService;
import com.ddf.microservicecloud.feignservice.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private UserClientService userClientService;

    /**
     * 调用微服务提供者的查询用户列表的接口
     * @return
     */
    @RequestMapping("/list1")
    public List<User> userList1() {
        return userClientService.queryAll();
    }

    @RequestMapping("/list")
    public List<User> userList() {
        return userFeignService.queryAll();
    }

    /**
     * 调用微服务提供者的查询用户列表的接口
     * @return
     */
    @RequestMapping("/user/{id}")
    public User userList(@PathVariable("id") Integer id) {
        return userFeignService.queryOne(id);
    }
}
