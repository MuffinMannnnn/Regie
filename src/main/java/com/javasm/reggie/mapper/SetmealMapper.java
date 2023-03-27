package com.javasm.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javasm.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/7 - 12:00
 * @Since:jdk1.8
 * @Description:和套餐相关的持久层
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
}
