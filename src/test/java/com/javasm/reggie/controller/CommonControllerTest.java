package com.javasm.reggie.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/8 - 20:03
 * @Since:jdk1.8
 * @Description:
 */
class CommonControllerTest {

    @Test
    void upload() {
        String fileName = "ereer.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}