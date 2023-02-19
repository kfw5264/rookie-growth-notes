package com.masq.basic.reflection.service;

import com.masq.basic.reflection.modle.User;

/**
 * @title TestService
 * @Author masq
 * @Date: 2021/8/20 上午11:31
 * @Version 1.0
 */
public interface UserService {

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    User getUserById(Integer id);

    /**
     * 添加用户
     * @param user 用户
     * @return 用户信息
     */
    User saveUser(User user);
}
