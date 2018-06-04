package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.UserClientService;
import com.ddf.microservicecloud.feign.feignservice.UserFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author DDf on 2018/5/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private UserClientService userClientService;

    @RequestMapping("/list1")
    public List<User> userList1() {
        return userClientService.queryAll();
    }

    @RequestMapping("/list")
    public List<User> userList() {
        return userFeignService.queryAll();
    }

    @RequestMapping("/user/{id}")
    public User userList(@PathVariable("id") Integer id) {
        return userFeignService.queryOne(id);
    }
}
