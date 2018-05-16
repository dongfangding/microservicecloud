package com.ddf.microservicecloud.service;

import com.ddf.microservicecloud.ApplicationTest;
import com.ddf.microservicecloud.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author DDf on 2018/5/14
 */
public class UserServiceTest extends ApplicationTest {
    @Autowired
    private UserService userService;

    @Test
    public void testQueryAll() {
        List<User> users = userService.queryAll();
        if (!users.isEmpty()) {
            users.forEach(user -> System.out.println(user.toString()));
        }
    }

    @Test
    public void testQueryOne() {
        System.out.println(userService.queryOne(1));
    }
}
