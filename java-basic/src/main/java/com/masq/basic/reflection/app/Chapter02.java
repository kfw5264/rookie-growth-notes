package com.masq.basic.reflection.app;

import com.masq.basic.reflection.modle.User;
import com.masq.basic.reflection.handler.MyHandler;
import com.masq.basic.reflection.handler.MyInterceptorForInvoke;
import com.masq.basic.reflection.handler.MyInterceptorForInvokeSuper;
import com.masq.basic.reflection.service.CGLIBService;
import com.masq.basic.reflection.service.UserService;
import com.masq.basic.reflection.service.UserServiceImpl;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @title DynamicProxyDemo
 * @Author masq
 * @Date: 2021/8/20 下午5:29
 * @Version 1.0
 */
public class Chapter02 {

    public static void main(String[] args) {
//
//        dynamicProxy();
//        dynamicProxyDemo();
        cglibProxyInvokeSuper();
        System.out.println("=====up is invokeSuper and down is invoke=============");
        cglibProxyInvoke();
    }

    private static void cglibProxyInvoke() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLIBService.class);
        enhancer.setCallback(new MyInterceptorForInvoke(new CGLIBService()));
        CGLIBService service = (CGLIBService) enhancer.create();
        service.methodA();
    }

    private static void cglibProxyInvokeSuper() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLIBService.class);
        enhancer.setCallback(new MyInterceptorForInvokeSuper());
        CGLIBService service = (CGLIBService) enhancer.create();
        service.methodA();
    }

    private static void dynamicProxyDemo() {
        UserService userService = new UserServiceImpl();
        UserService proxyInstance = (UserService) Proxy
                .newProxyInstance(userService.getClass().getClassLoader(),
                        userService.getClass().getInterfaces(),
                        new MyHandler(userService));
        User user = proxyInstance.getUserById(1);
        System.out.println(user);

        User saveUser = new User(4, "Lucy", "333555", "露西", 15, "女");
        User user1 = proxyInstance.saveUser(saveUser);
        System.out.println(user1);

    }

    private static void dynamicProxy() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName1 = "test";
                String methodName2 = "testReturn";
                String methodName3 = "testReturnAndParam";
                if (methodName1.equals(method.getName())) {
                    System.out.println("This is a TestMethodNoReturnAndNoParameter");
                }
                if (methodName2.equals(method.getName())) {
                    System.out.println("This is a TestMethodReturn");
                    return "Hello World";
                }
                if (methodName3.equals(method.getName())) {
                    System.out.println("This is a TestMethodReturnWithOneParameter");
                    return "Hello " + args[0] + "level" + args[1];
                }

                return null;
            }
        };

        TestInterface ti = (TestInterface)Proxy.newProxyInstance(Hello.class.getClassLoader(),
                new Class[]{TestInterface.class},
                handler);

        ti.test();
        System.out.println(ti.testReturn());
        System.out.println(ti.testReturnAndParam("Jim", 3));
    }

    interface Hello {
        void sayHello(String msg);
    }

    interface TestInterface {
        void test();
        String testReturn();
        String testReturnAndParam(String msg, int level);
    }
}
