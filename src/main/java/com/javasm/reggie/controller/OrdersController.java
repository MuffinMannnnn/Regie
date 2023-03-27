package com.javasm.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javasm.reggie.common.R;
import com.javasm.reggie.dto.OrdersDto;
import com.javasm.reggie.entity.OrderDetail;
import com.javasm.reggie.entity.Orders;
import com.javasm.reggie.service.OrderDetailService;
import com.javasm.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/16 - 22:34
 * @Since:jdk1.8
 * @Description:
 */
@RequestMapping("/order")
@RestController
@Slf4j
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;



    /**
     * 后台查询订单明细
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number,String beginTime,String endTime){
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();

        //添加查询条件  动态sql  字符串使用StringUtils.isNotEmpty这个方法来判断
        //这里使用了范围查询的动态SQL，这里是重点！！！
        queryWrapper.like(number!=null,Orders::getNumber,number)
                .gt(StringUtils.isNotEmpty(beginTime),Orders::getOrderTime,beginTime)
                .lt(StringUtils.isNotEmpty(endTime),Orders::getOrderTime,endTime);

        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {

        log.info("用户的订单数据：{}", orders);
        ordersService.submit(orders);

        return R.success("下单成功");
    }

    /**
     * 用户端展示自己的订单分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize) {
        //分页构造器
        Page<Orders> pageInfo = new Page<>(page, pageSize);

        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        Page<OrdersDto> ordersDtoPage = new Page<>();

        //添加排序条件，按照结账时间进行降序排序
        queryWrapper.orderByDesc(Orders::getOrderTime);


        //执行分页查询
        ordersService.page(pageInfo, queryWrapper);

        //对象拷贝,为OrdersDto赋值
        List<Orders> records = pageInfo.getRecords();
        BeanUtils.copyProperties(pageInfo, ordersDtoPage, "records");

        List<OrdersDto> list = null;
        list = records.stream().map(item -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item, ordersDto);
            //对ordersDto进行orderDetails属性的赋值
            //获取订单id
            Long orderId = item.getId();
            //根据id查询订单细节
            List<OrderDetail> orderDetailList = this.getOrderDetailListByOrderId(orderId);
            ordersDto.setOrderDetails(orderDetailList);
            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtoPage.setRecords(list);

        return R.success(ordersDtoPage);

    }


    /**
     * 抽离出一个方法，避免数据库循环调用
     *
     * @param orderId
     * @return
     */
    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        return orderDetailList;
    }


}
