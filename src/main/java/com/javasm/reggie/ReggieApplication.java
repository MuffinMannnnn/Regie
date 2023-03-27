package com.javasm.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/2/28 - 22:53
 * @Since:jdk1.8
 * @Description:
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement //开启事务注解的支持
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功");
    }
}
