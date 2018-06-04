package com.ddf.microservicecloud.feign.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author DDf on 2018/6/3
 */
@RestController
public class ScheduleController {
    private Logger log = LoggerFactory.getLogger(ScheduleController.class);

    @Scheduled(cron = "0/10 * * * * *")
    public void scheduleDemo() {
        log.info("scheduleDemo...........{}", LocalDateTime.now());
    }
}
