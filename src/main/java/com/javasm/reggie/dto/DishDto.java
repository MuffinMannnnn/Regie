package com.javasm.reggie.dto;


import com.javasm.reggie.entity.Dish;
import com.javasm.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    //菜品所对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();
    //菜品所对应的套餐名称
    private String categoryName;

    private Integer copies;
}
