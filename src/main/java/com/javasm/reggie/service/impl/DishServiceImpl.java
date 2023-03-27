package com.javasm.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.reggie.common.CustomException;
import com.javasm.reggie.common.R;
import com.javasm.reggie.dto.DishDto;
import com.javasm.reggie.entity.Dish;
import com.javasm.reggie.entity.DishFlavor;
import com.javasm.reggie.entity.Setmeal;
import com.javasm.reggie.mapper.DishMapper;
import com.javasm.reggie.service.DishFlavorService;
import com.javasm.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/7 - 20:24
 * @Since:jdk1.8
 * @Description:
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Override
    @Transactional //因为涉及到了多张表的操作，需要加入事务控制
    public void saveWithFlavor(DishDto dishDto) {
        //先保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId(); // 菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item ->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());



        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {

        //查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish,dishDto);


        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表的基本信息
        this.updateById(dishDto);
        //清理当前菜品对应口味的数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据--dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map(item ->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }



    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<DishFlavor> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据菜品的id进行查询
        dishLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        //将查询结果代入MP中的remove方法，对多个id进行删除操作，此时菜品相关的口味信息删除完成
        dishFlavorService.remove(dishLambdaQueryWrapper);
        //删除指定菜品
        super.removeById(id);
    }


    /**
     * 根据id对菜品的状态信息进行更改
     * @param id
     */
    @Override
    public void updateDishStatus(List<Long> id) {


        LambdaUpdateWrapper<Dish> dishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();

        //添加查询条件,根据菜品的id进行查询
        List<Dish> dishes = super.listByIds(id);
        for (Dish dish : dishes) {
            if (dish.getStatus() == 1){
                dishLambdaUpdateWrapper.eq(Dish::getId,id).set(Dish::getStatus,0);
            }else {
                dishLambdaUpdateWrapper.eq(Dish::getId,id).set(Dish::getStatus,1);
            }
        }



        //将查询的结果代入MP中的update方法
        super.update(dishLambdaUpdateWrapper);
    }
}
