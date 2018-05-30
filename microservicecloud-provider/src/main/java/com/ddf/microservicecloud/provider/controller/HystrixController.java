package com.ddf.microservicecloud.provider.controller;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DDf on 2018/5/29
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private UserService userService;

    /**
     *  @HystrixCommand 开启Hystrix功能之后，该命令可以给指定方法如果出现错误不能返回预期结果时指定一个最终执行的方法，
     *  如下，指定了hystrixListFallback方法，下面的实例故意抛出一个异常，那么hystrixListFallback就会执行，经过测试，fallback
     *  的方法返回值必须和原方法保持一致
     *  如下实例返回结果如下
     *[
         * {
             * id: 0,
             * userName: "hystrixList出现错误，现在执行服务熔断。。。",
             * password: "hystrixList出现错误，现在执行服务熔断。。。",
             * gender: "hystrixList出现错误，现在执行服务熔断。。。",
             * tel: "hystrixList出现错误，现在执行服务熔断。。。",
             * dbSource: "dbSource-01",
             * removed: 0
         * }
     * ]
     *
     * @return
     */
    @RequestMapping("hystrixList")
    @HystrixCommand(fallbackMethod = "hystrixListFallback")
    public List<User> hystrixList() {
        if (true) {
            throw new RuntimeException();
        }
        return userService.queryAll();
    }

    public List<User> hystrixListFallback() {
        List<User> rtnList = new ArrayList<>();
        User user = new User(0, "hystrixList出现错误，现在执行服务熔断。。。", "hystrixList出现错误，现在执行服务熔断。。。",
                "hystrixList出现错误，现在执行服务熔断。。。", "hystrixList出现错误，现在执行服务熔断。。。");
        rtnList.add(user);
        return rtnList;
    }
}
