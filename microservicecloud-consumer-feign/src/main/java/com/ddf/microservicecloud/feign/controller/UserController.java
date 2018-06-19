package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.feign.feignservice.UserClientService;
import com.ddf.microservicecloud.feign.feignservice.UserFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/list1")
    public List<User> userList1() {
        return userClientService.queryAll();
    }

    @RequestMapping("/list")
    public List<User> userList() {
        try {
            List<User> userList = (List<User>) redisTemplate.opsForHash().get("user", "users");
            log.info("从redis中获取数据。。。。。");
            log.info("获得数据： {}", userList);
            if (userList == null || userList.isEmpty()) {
                // 为保险起见，如果数据为空，发送http请求来获得一次最新的数据
                log.info("从redis中获得数据为空，尝试发送请求获取数据。。。。");
                return userFeignService.queryAll();
            }
            return userList;
        } catch (Exception e) {
            // 如果从redis中获取失败，那么再尝试发送Http请求获取数据
            log.info("从redis中获得数据失败，尝试发送请求获取数据。。。。");
            return userFeignService.queryAll();
        }
    }

    @RequestMapping("/user/{id}")
    public User userList(@PathVariable("id") Integer id) {
        return userFeignService.queryOne(id);
    }

    @RequestMapping("update")
    public User updateOne(User user) {
        System.out.println(user.toString());
        return userFeignService.updateOne(user);
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userFeignService.delete(id);
    }
}
