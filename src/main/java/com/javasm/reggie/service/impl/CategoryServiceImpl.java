package com.javasm.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.reggie.common.CustomException;
import com.javasm.reggie.entity.Category;
import com.javasm.reggie.entity.Dish;
import com.javasm.reggie.entity.Setmeal;
import com.javasm.reggie.mapper.CategoryMapper;
import com.javasm.reggie.service.CategoryService;
import com.javasm.reggie.service.DishService;
import com.javasm.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/6 - 22:45
 * @Since:jdk1.8
 * @Description:
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count1 > 0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类等下关联了菜品,不能删除");
        }

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类等下关联了套餐,不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
