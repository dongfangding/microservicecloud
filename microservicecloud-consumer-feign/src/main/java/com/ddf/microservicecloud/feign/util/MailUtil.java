package com.ddf.microservicecloud.feign.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author DDf on 2018/5/31
 */
@Component
public class MailUtil {
    private Logger log = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送简单的纯文本内容的邮件
     * @param sendTo 接收人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String[] sendTo, String subject, String content) {
        log.debug("sendSimpleMail.....to{}...............", sendTo.toString());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setTo(sendTo);
        // 经过测试，这个必须要写，而且必须要和配置的邮箱认证的用户名一致
        message.setFrom("1041765757@qq.com");
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送带附件的和支持html格式内容的邮件内容
     * @param sendTo 收件人
     * @param subject 主题
     * @param conent 内容
     * @param attachment 附件
     * @throws MessagingException
     */
    public void sendMimeMail(String[] sendTo, String subject, String conent, Map<String, File> attachment)
            throws MessagingException {
        log.debug("sendMimeMail.....to {}...............", sendTo.toString());
        //1、创建一个复杂的消息邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //邮件设置
        helper.setSubject(subject);
        helper.setText(conent,true);

        helper.setTo(sendTo);
        // 经过测试，这个必须要写，而且必须要和配置的邮箱认证的用户名一致
        helper.setFrom("1041765757@qq.com");

        //上传文件
        if (attachment != null && !attachment.isEmpty()) {
            attachment.forEach((attachmentFilename, file) -> {
                try {
                    helper.addAttachment(attachmentFilename, file);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        mailSender.send(mimeMessage);
    }

    public void sendMimeMail(String[] sendTo, String subject, String conent) throws MessagingException {
        sendMimeMail(sendTo, subject, conent, null);
    }
}
