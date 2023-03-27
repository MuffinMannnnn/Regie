package com.javasm.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.reggie.entity.OrderDetail;
import com.javasm.reggie.mapper.OrderDetailMapper;
import com.javasm.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/16 - 22:31
 * @Since:jdk1.8
 * @Description:
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper,OrderDetail> implements OrderDetailService {
}
