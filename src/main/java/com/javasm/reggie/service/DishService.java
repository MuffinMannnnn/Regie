package com.javasm.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.reggie.dto.DishDto;
import com.javasm.reggie.entity.Dish;

import java.util.List;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/7 - 20:18
 * @Since:jdk1.8
 * @Description:
 */
public interface DishService extends IService<Dish> {

    /*
    新增菜品，同时插入菜品对应的口味数据
    需要操作两张表：dish，dish_flavor
     */

    void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息
    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    void updateWithFlavor(DishDto dishDto);

    //根据id删除菜品
    void remove(Long id);

    //根据id对菜品状态信息进行更改
    void updateDishStatus(List<Long> id);
}
