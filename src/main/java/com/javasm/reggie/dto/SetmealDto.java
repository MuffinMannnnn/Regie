package com.javasm.reggie.dto;


import com.javasm.reggie.entity.Setmeal;
import com.javasm.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
