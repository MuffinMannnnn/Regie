package com.javasm.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javasm.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/14 - 19:45
 * @Since:jdk1.8
 * @Description:和用户相关的持久层
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
