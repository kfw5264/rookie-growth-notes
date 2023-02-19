package com.masq.basic.annotations;

/**
 * @title Person
 * @Author masq
 * @Date: 2021/8/31 下午2:33
 * @Version 1.0
 */
public class Person {

    @Range(min = 3, max = 25)
    private String username;

    @Range(min = 6, max = 100)
    private String password;

    @Range(min = 6)
    private String email;

    @Range(max = 11)
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
