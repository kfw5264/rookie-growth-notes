package com.masq.mybatisdemo.service.impl;

import com.masq.mybatisdemo.pojo.Order;
import com.masq.mybatisdemo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderServiceImplTest {

    @Resource
    private OrderService orderService;
    @Test
    public void testInset() {
        Order order = new Order(1L, "a", 1);
        Integer orderId = orderService.insert(order);
        System.out.println(orderId);
    }

    @Test
    public void testUpdate() {
        Order order = new Order(1L, "b", 2);
        orderService.update(order);
    }

    @Test
    public void testInsertBatch() {
        List<Order> list = new ArrayList<>();
        list.add(new Order(2L, "NO.2", 1));
        list.add(new Order(3L, "NO.3", 2));
        list.add(new Order(4L, "NO.4", 2));
        list.add(new Order(5L, "NO.5", 1));
        orderService.insertBatch(list);
    }

    @Test
    public void testUpdateBatch() {
        List<Order> list = new ArrayList<>();
        list.add(new Order(1L, "NO.0001", 1));
        list.add(new Order(2L, "NO.0002", 2));
        list.add(new Order(3L, "NO.0003", 1));
        list.add(new Order(4L, "NO.0004", 2));
        list.add(new Order(5L, "NO.0005", 1));

        list.forEach(item -> {
            System.out.println("CreateBy->" + item.getInsertBy() + ", UpdateBy->" + item.getUpdateBy() + ", createTime->" + item.getInsertTime() + ", updateTime-> " + item.getUpdateTime());
        });

        orderService.updateBatch(list);
    }
}
