package com.javasm.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.reggie.entity.ShoppingCart;
import com.javasm.reggie.mapper.ShoppingCartMapper;
import com.javasm.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/16 - 17:44
 * @Since:jdk1.8
 * @Description:
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
