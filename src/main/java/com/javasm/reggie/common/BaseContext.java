package com.javasm.reggie.common;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/6 - 21:54
 * @Since:jdk1.8
 * @Description: 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
