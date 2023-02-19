package com.masq.basic.reflection.modle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title User
 * @Author masq
 * @Date: 2021/8/20 上午11:31
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Integer age;
    private String sex;
}
