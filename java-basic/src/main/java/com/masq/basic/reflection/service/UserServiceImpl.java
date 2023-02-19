package com.masq.basic.reflection.service;

import com.masq.basic.reflection.modle.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @title TestServiceImpl
 * @Author masq
 * @Date: 2021/8/20 下午1:47
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {
    static Map<Integer, User> map = null;
    static {
        map = new HashMap<>();
        map.put(1, new User(1, "Tom", "123456", "汤姆", 13, "男"));
        map.put(2, new User(2, "Jerry", "222333", "杰瑞", 12, "女"));
        map.put(3, new User(3, "Lily", "111444", "莉莉", 14, "女"));
    }

    @Override
    public User getUserById(Integer id) {
        System.out.printf("根据用户id[%d]获取用户信息%n", id);
        return map.get(id);
    }

    @Override
    public User saveUser(User user) {
        User user1 = map.get(user.getId());
        if (null != user1) {
            System.out.printf("用户id[%d]已存在%n", user.getId());
            return null;
        }
        map.put(user.getId(), user);
        return user;
    }
}
