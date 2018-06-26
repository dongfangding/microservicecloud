package com.ddf.microservicecloud.feign.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author DDf on 2018/6/25
 */
@RestController
@RequestMapping("/jest")
public class JestController {
    @Autowired
    private JestService jestService;

    @RequestMapping("/indexAllUser")
    public void indexAllUser() throws IOException {
        jestService.indexAllUser();
    }

    @RequestMapping("/createIndexMapping")
    public void createIndexMapping() throws IOException {
        jestService.createIndexMapping();
    }

    @RequestMapping("/queryMatch")
    public String queryMatch() {
        return jestService.queryMatch();
    }
}
