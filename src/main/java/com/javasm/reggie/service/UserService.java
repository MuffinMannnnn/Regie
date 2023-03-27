package com.javasm.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.reggie.entity.User;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/14 - 19:41
 * @Since:jdk1.8
 * @Description:
 */
public interface UserService extends IService<User> {

    //发送邮件
    void sendMsg(String to,String subject,String text);
}
