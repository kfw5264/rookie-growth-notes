package com.masq.basic.reflection.service;

/**
 * @title GCLB_UserService
 * @Author masq
 * @Date: 2021/8/20 下午3:40
 * @Version 1.0
 */
public class CGLIBService {

    public void methodA() {
        System.out.println("GCLIBService --> methodA");
        methodB();
    }

    public void methodB() {
        System.out.println("GCLIBService --> methodB");
    }
}
