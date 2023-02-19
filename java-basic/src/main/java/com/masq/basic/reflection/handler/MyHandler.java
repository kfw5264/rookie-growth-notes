package com.masq.basic.reflection.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @title LogHandler
 * @Author masq
 * @Date: 2021/8/20 上午11:24
 * @Version 1.0
 */
public class MyHandler implements InvocationHandler {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Object target;

    public MyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(method);
        Object result = method.invoke(target, args);
        after(method);
        return result;
    }

    private void before(Method method) {
        System.out.printf("开始调用方法[%s], 当前时间%s%n", method.getName(), SIMPLE_DATE_FORMAT.format(new Date()));
    }

    private void after(Method method) {
        System.out.printf("调用方法[%s]结束, 当前时间%s%n", method.getName(), SIMPLE_DATE_FORMAT.format(new Date()));
    }
}
