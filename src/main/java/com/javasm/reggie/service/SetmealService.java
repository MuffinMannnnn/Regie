package com.javasm.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.reggie.dto.SetmealDto;
import com.javasm.reggie.entity.Setmeal;

import java.util.List;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/7 - 20:23
 * @Since:jdk1.8
 * @Description:
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联关系
     * @param ids
     */
    void removeWithDish(List<Long> ids);
}
