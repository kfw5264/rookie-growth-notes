# Java学习笔记

在工作及学习过程中发现基础以及一些框架原理方面还有很多问题存在，通过重新梳理知识来让自己更好的应对各种问题。磨刀不误砍柴功，良好的基础是成长必备的素质。

## Java基础

### [异常](./java-basic/src/main/java/com/masq/basic/exception/Java异常.md)

1. Java异常
2. 捕获异常
   - `try...catch`
   - `finally`
   - `try-with-resource`
3. 抛出异常
   - 异常的传播
   - 抛出异常
   - 异常屏蔽

###  [反射](./java-basic/src/main/java/com/masq/basic/reflection/反射.md)

1. `Class`类
   - 获取`Class`类实例的方法
   - `Class`类的常用方法
2. 访问字段
3. 调用方法
4. 调用构造方法
5. 获取继承关系
6. 动态代理
   - 通过实现接口的方式  -> `JDK`动态代理
   - 通过继承类的方式  ->  `CGLIB(Code Generation Library)`动态代理

### [注解](./java-basic/src/main/java/com/masq/basic/annotations/Java注解.md)

1. 注解的作用
2. 定义注解
   - 元注解
   - 定义注解的流程
3. 处理注解
   - 读取注解
   - 使用注解

## [泛型](./java-basic/src/main/java/com/masq/basic/generosity/泛型.md)

1. 什么是泛型？泛型的作用
2. 编写泛型
3. `Extends`通配符
4. `Super`通配符
5. `PECS`原则
6. 无限定通配符
