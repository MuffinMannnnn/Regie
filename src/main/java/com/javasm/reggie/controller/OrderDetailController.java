package com.javasm.reggie.controller;

import com.javasm.reggie.service.OrderDetailService;
import com.javasm.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/16 - 22:34
 * @Since:jdk1.8
 * @Description:
 */
@RequestMapping("/orderDetail")
@RestController
@Slf4j
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService ;


}
