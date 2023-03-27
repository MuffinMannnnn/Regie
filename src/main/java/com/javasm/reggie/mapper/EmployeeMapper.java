package com.javasm.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javasm.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/1 - 20:13
 * @Since:jdk1.8
 * @Description:
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
