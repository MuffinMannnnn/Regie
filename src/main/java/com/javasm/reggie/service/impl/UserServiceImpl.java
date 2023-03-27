package com.javasm.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.reggie.entity.User;
import com.javasm.reggie.mapper.UserMapper;
import com.javasm.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/14 - 19:44
 * @Since:jdk1.8
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    //把yml配置的邮箱号赋值到from
    @Value("${spring.mail.username}")
    private String from;
    //发送邮件需要的对象
    @Autowired
    private JavaMailSender javaMailSender;
    //邮件发送人
    @Override
    public void sendMsg(String to, String subject, String text) {
        //发送简单邮件，简单邮件不包含附件等别的信息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        //发送邮件
        javaMailSender.send(message);

    }
}
