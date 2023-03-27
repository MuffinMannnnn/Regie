package com.javasm.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.reggie.entity.Orders;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/16 - 22:29
 * @Since:jdk1.8
 * @Description:
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 实现用户下单
     * @param orders
     */
    void submit(Orders orders);
}
