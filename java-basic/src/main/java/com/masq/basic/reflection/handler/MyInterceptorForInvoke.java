package com.masq.basic.reflection.handler;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @title MyInterceptor
 * @Author masq
 * @Date: 2021/8/20 下午3:41
 * @Version 1.0
 *
 * 需要引入CGLIB的jar包
 */
public class MyInterceptorForInvoke implements MethodInterceptor {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Object target;

    public MyInterceptorForInvoke() {}

    public MyInterceptorForInvoke(Object target) {
        this.target = target;
    }

    /**
     *
     * @param o 增强的对象
     * @param method  拦截的方法
     * @param objects 参数列表
     * @param methodProxy 方法的代理，通过invokeSuper方法调用
     * @return 执行结果
     * @throws Throwable 异常信息
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before(method);
        Object result = methodProxy.invoke(target, objects);
        after(method);
        return result;
    }

    private void before(Method method) {
        System.out.printf("开始执行方法%s, 时间%s%n", method.getName(), SIMPLE_DATE_FORMAT.format(new Date()));
    }

    private void after(Method method) {
        System.out.printf("执行方法完成%s, 时间%s%n", method.getName(), SIMPLE_DATE_FORMAT.format(new Date()));
    }
}
