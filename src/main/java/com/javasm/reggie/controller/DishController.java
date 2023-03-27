package com.javasm.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javasm.reggie.common.R;
import com.javasm.reggie.dto.DishDto;
import com.javasm.reggie.entity.Category;
import com.javasm.reggie.entity.Dish;
import com.javasm.reggie.entity.DishFlavor;
import com.javasm.reggie.service.CategoryService;
import com.javasm.reggie.service.DishFlavorService;
import com.javasm.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/7 - 22:51
 * @Since:jdk1.8
 * @Description:菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }


    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);


        return R.success(dishDto);
    }


    /**
     * 菜品信息的分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){

        //分页构造器
        Page<Dish> pageInfo = new Page<>(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        Page<DishDto> dishDtoPage = new Page<>();


        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);

        //添加排序条件，根据更新时间进行降序排序
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //添加过滤条件，删除的状态不能为1
        queryWrapper.ne(Dish :: getIsDeleted,1);

        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = null;

        list = records.stream().map(item ->{
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id

            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }


            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }


    /**
     * 根据id对菜品进行批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleter(Long[] ids){
        log.info("删除菜品,id为：{}",ids);

        Arrays.stream(ids).map(id ->{
            Dish dish = dishService.getById(id);
            dish.setIsDeleted(1);
            return dish;
        }).forEach(dish -> dishService.updateById(dish));


        return R.success("当前菜品已成功删除");
    }

    /**
     * 根据id对菜品的状态信息进行更改
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> update(@PathVariable Integer status,Long[] ids){
        log.info("根据id修改菜品状态为：{},id为：{}",status,ids);
        //通过id查询数据库。修改id为ids数组中的数据的菜品状态status为前端页面提交的status


        /*
        for (int i = 0; i < ids.length; i++) {
      Long id=ids[i];
      //根据id得到每个dish菜品。
      Dish dish = dishService.getById(id);
      dish.setStatus(status);
      dishService.updateById(dish);
      }
         */

        /*
        这里使用了Lambda表达式来简化代码，与上述代码功能相同，
        将ids数组转换为一个流，使用map方法将每个id映射为对应的Dish对象，
        并修改其状态。最后使用forEach方法将每个Dish对象传递给updateById方法进行更新。
         */
        Arrays.stream(ids).map(id -> {
            Dish dish = dishService.getById(id);
            dish.setStatus(status);
            return dish;
        }).forEach(dish -> dishService.updateById(dish));

        return R.success("修改菜品成功");

    }

//    /**
//     * 根据条件查询对应的菜品数据(旧版本)
//     * @param dish
//     * @return
//     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        //构建查询条件
//        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        dishLambdaQueryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId())
//                .eq(Dish::getStatus,1);//添加套件,查询状态为1（起售状态）的菜品
//        //添加排序条件
//        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
//
//        return R.success(list);
//    }

        @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //构建查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId())
                .eq(Dish::getStatus,1);//添加条件,查询状态为1（起售状态）的菜品
        //添加排序条件
        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(dishLambdaQueryWrapper);


            List<DishDto> dishDtoList = list.stream().map(item ->{
                DishDto dishDto = new DishDto();

                BeanUtils.copyProperties(item,dishDto);

                Long categoryId = item.getCategoryId();//分类id

                //根据id查询分类对象
                Category category = categoryService.getById(categoryId);

                if (category != null){
                    String categoryName = category.getName();
                    dishDto.setCategoryName(categoryName);
                }

                //当前菜品的id
                Long dishId = item.getId();
                LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(DishFlavor::getDishId,dishId);

                List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper);
                dishDto.setFlavors(dishFlavorList);

                return dishDto;
            }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }





}
