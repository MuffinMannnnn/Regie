package com.javasm.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.reggie.entity.Employee;
import com.javasm.reggie.mapper.EmployeeMapper;
import com.javasm.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/1 - 20:15
 * @Since:jdk1.8
 * @Description:
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
