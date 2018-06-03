package com.ddf.microservicecloud.feign.controller;

import com.ddf.microservicecloud.feign.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
/**
 * @author DDf on 2018/5/31
 */
@RestController
public class MailController {
    @Autowired
    private MailUtil mailUtil;
    /**
     * 发送一个简单的纯文本的邮件内容
     */
    @RequestMapping("/simpleMail")
    public void sendSimpleMail() {
        mailUtil.sendSimpleMail(new String[]{"1041765757@qq.com", "dongfang.ding@hitisoft.com",
                "18356784598@163.com"}, "邮件测试", "系统测试发送邮件，请勿回复。。。。。。。。。");
    }
    /**
     * 发送一个支持html格式内容和附件的邮件
     */
    @RequestMapping("/mimeMail")
    public void sendMimeMail() throws MessagingException, FileNotFoundException {
        Map<String, File> attachMap = new HashMap<>();
        attachMap.put("README.md", ResourceUtils.getFile("classpath:attach/README.md"));
        attachMap.put("sendMimeMail.png", ResourceUtils.getFile("classpath:attach/sendMimeMail.png"));
        mailUtil.sendMimeMail(new String[]{"1041765757@qq.com", "dongfang.ding@hitisoft.com",
                        "18356784598@163.com"}, "附件邮件测试", "<b style='color:red'>这是一个带附件的邮件测试</b>",
                attachMap);
    }
}