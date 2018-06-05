package com.ddf.microservicecloud.provider.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public User test() {
        User user = new User(1, "ddf", "123456", "男", "18300001111");
        return user;
    }

    @RequestMapping("/list")
    public List<User> userList() {
        return userService.queryAll();
    }

    @RequestMapping("/user/{id}")
    public User user(@PathVariable Integer id) {
        return userService.queryOne(id);
    }

    @RequestMapping("/list1")
    public List<Map<String, Object>> userMapList() {
        System.out.println("1111111111111");
        return userService.queryAllMap();
    }


    @RequestMapping("/update")
    public User user(@RequestBody User user) {
        return userService.updateOne(user);
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
