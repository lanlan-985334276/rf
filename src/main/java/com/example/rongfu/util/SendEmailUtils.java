package com.example.rongfu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendEmailUtils {
    @Value("${spring.mail.username}")
    private static String from;
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String content) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 邮件发送者
        message.setTo(to); // 邮件接受者
        message.setSubject("【RongFu】验证码"); // 主题
        message.setText(content); // 内容
        mailSender.send(message);
    }
}
