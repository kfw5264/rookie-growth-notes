package com.masq.mybatisdemo.service.impl;

import com.masq.mybatisdemo.dao.OrderDao;
import com.masq.mybatisdemo.pojo.Order;
import com.masq.mybatisdemo.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Override
    public Integer insert(Order order) {
        return orderDao.insert(order);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public void insertBatch(List<Order> list) {
        orderDao.insertBatch(list);
    }

    @Override
    public void updateBatch(List<Order> list) {
        orderDao.updateBatch(list);
    }
}
