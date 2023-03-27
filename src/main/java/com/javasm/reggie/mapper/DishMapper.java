package com.javasm.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javasm.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/7 - 11:59
 * @Since:jdk1.8
 * @Description:
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
