package com.ddf.microservicecloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DDf on 2018/5/28
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "测试";
    }
}
