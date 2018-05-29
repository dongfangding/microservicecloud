package com.ddf.microservicecloud.controller;

import com.ddf.microservicecloud.clientservice.UserClientService;
import com.ddf.microservicecloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserClientService userClientService;
    @Autowired
    private UserClientService1 userClientService1;

    /**
     * 调用微服务提供者的查询用户列表的接口
     * @return
     */
    @RequestMapping("/list")
    public List<User> userList() {
        return userClientService.queryAll();
    }

    @RequestMapping("maplist")
    public List<Map<String, Object>> userMapList() {
        return userClientService.queryAllMap();
    }

    /**
     * 调用微服务提供者的查询用户列表的接口
     * @return
     */
    @RequestMapping("/list1")
    public List<User> userList1() {
        return userClientService1.queryAll();
    }
}
