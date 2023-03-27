package com.javasm.reggie.common;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/7 - 21:24
 * @Since:jdk1.8
 * @Description:自定义业务异常
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message); // 表示调用父类的有参构造器
    }
}
