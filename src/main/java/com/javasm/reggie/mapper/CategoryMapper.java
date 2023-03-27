package com.javasm.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javasm.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/6 - 22:44
 * @Since:jdk1.8
 * @Description:
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
