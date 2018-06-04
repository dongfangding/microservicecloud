package com.ddf.microservicecloud.feign.service.impl;

import com.ddf.microservicecloud.feign.controller.AsyncController;
import com.ddf.microservicecloud.feign.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author DDf on 2018/6/4
 */
@Service
public class AsyncServiceImpl implements AsyncService {
    private Logger log = LoggerFactory.getLogger(AsyncServiceImpl.class);

    /**
     * @Async表明这是一个异步方法
     */
    @Async
    @Override
    public void asyncPrint() {
        try {
            Thread.sleep(3000);
            log.info("这是一个异步的打印方法..................");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
