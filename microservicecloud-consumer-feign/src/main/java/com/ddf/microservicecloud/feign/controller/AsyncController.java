package com.ddf.microservicecloud.feign.controller;

        import com.ddf.microservicecloud.feign.service.AsyncService;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

/**
 * @author DDf on 2018/6/4
 */
@RestController
@RequestMapping("/async")
public class AsyncController {
    private Logger log = LoggerFactory.getLogger(AsyncController.class);
    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/asyncPrint")
    public void asyncPrint() {
        log.info("方法执行开始........");
        asyncService.asyncPrint();
        log.info("方法执行结束........");
    }
}
