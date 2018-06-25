package com.ddf.microservicecloud.feign.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DDf on 2018/6/25
 */
@RestController
@RequestMapping("/jest")
public class JestController {
    @Autowired
    private JestService jestService;

    @RequestMapping("/indexAllUser")
    public void indexAllUser() {
        jestService.indexAllUser();
    }

    @RequestMapping("/getUserNamef")
    public String getUserNamef() {
        return jestService.searchDemo1();
    }
}
