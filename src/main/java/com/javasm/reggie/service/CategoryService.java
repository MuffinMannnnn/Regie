package com.javasm.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.reggie.entity.Category;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/6 - 22:44
 * @Since:jdk1.8
 * @Description:
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
