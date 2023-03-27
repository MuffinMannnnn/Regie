package com.javasm.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javasm.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/16 - 22:27
 * @Since:jdk1.8
 * @Description:
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
