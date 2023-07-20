package com.masq.mybatisdemo.dao;

import com.masq.mybatisdemo.pojo.Order;

import java.util.List;

public interface OrderDao {

    Integer insert(Order order);

    void update(Order order);

    void insertBatch(List<Order> list);

    void updateBatch(List<Order> list);
}
