package com.masq.basic.reflection.app;

import com.masq.basic.reflection.modle.Son;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @title App
 * @Author masq
 * @Date: 2021/8/19 上午9:38
 * @Version 1.0
 */
public class Chapter01 {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
//        basicClassInfo("".getClass());
//        basicClassInfo(Serializable.class);
//        basicClassInfo(String[].class);
//        basicClassInfo(int.class);
//        basicClassInfo(Class.forName("java.lang.Override"));
//        basicClassInfo(short.class);
//        basicClassInfo(ArrayList.class);
        Class sonClz = Son.class;
//        getDeclaredFields(sonClz);
//        getFields(sonClz);
//        getDeclaredMethods(sonClz);
//        getMethods(sonClz);
//        newInstance(sonClz);
//        getAndSetFieldValue();

//        invokeMethod();
//        invokeConstructor();
//        getExtendInfo();

    }



    private static void getExtendInfo() {
        Class<Integer> integerClass = Integer.class;
        Class<? super Integer> superclass = integerClass.getSuperclass();
        System.out.println(superclass.getName());

        Class<?>[] interfaces = integerClass.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println(anInterface.getName());
        }

        System.out.println("Integer -> Number:" + Integer.class.isAssignableFrom(Number.class));
        System.out.println("Number -> Integer:" + Number.class.isAssignableFrom(Integer.class));
    }

    private static void invokeConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Person> personClass = Person.class;
        Constructor<Person> constructorWithStringParameter = personClass.getDeclaredConstructor(String.class);
        Person person = constructorWithStringParameter.newInstance("Jerry");
        System.out.println(person.name);
    }

    private static void invokeMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Person person = new Person("Tom");
        Method saySomethingMethod = person.getClass().getDeclaredMethod("saySomething", String.class);
        saySomethingMethod.invoke(person, "Hello world");

        // 多态
        Method studentSaySomethingMethod = person.getClass().getDeclaredMethod("saySomething", String.class);
        studentSaySomethingMethod.invoke(new Student("Harry"), "Hello World");
    }

    private static void getAndSetFieldValue() throws NoSuchFieldException, IllegalAccessException {
        Person p = new Person("Tom");
        Class<? extends Person> pClass = p.getClass();
        Field nameField = pClass.getDeclaredField("name");
        nameField.setAccessible(true);
        Object o = nameField.get(p);
        System.out.println(o);

        nameField.set(p, "Jerry");
        System.out.println(nameField.get(p));
    }

    static class Person {
        private String name;

        public Person() {}

        public Person (String name) {
            this.name = name;
        }

        public void saySomething(String msg) {
            System.out.println("Person say " + msg);
        }

        public void saySomething(String msg, int times) {
            this.saySomething(msg);
            System.out.println("say " + times + " times");
        }

    }

    static class Student extends Person {

        public Student(String name) {
            super(name);
        }

        @Override
        public void saySomething(String msg) {
            System.out.println("Student say " + msg);
        }
    }

    private static void newInstance(Class clz) throws InstantiationException, IllegalAccessException {
        clz.newInstance();
    }

    private static void getMethods(Class clz) {
        for (Method method : clz.getMethods()) {
            System.out.println("MODIFIERS:" + method.getModifiers()
                    + "——Type:" + method.getReturnType().getName()
                    +  "——FIELD_NAME:" + method.getName());
        }
    }

    private static void getDeclaredMethods(Class clz) {
        Method[] declaredMethods = clz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println("MODIFIERS:" + declaredMethod.getModifiers()
                    + "——Type:" + declaredMethod.getReturnType().getName()
                    +  "——FIELD_NAME:" + declaredMethod.getName());
        }
    }

    private static void getFields(Class clz) {
        Field[] fields = clz.getFields();
        for (Field field : fields) {
            System.out.println("MODIFIERS:" + field.getModifiers()
                    + "——Type:" + field.getType().getName()
                    +  "——FIELD_NAME:" + field.getName());
        }
    }

    private static void getDeclaredFields(Class clz) {
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("MODIFIERS:" + declaredField.getModifiers()
                    + "——Type:" + declaredField.getType().getName()
                    +  "——FIELD_NAME:" + declaredField.getName());
        }
    }

    private static void basicClassInfo(Class clz) {
        System.out.println("========================================");
        System.out.println("NAME————" + clz.getName());
        System.out.println("SIMPLE_NAME————" + clz.getSimpleName());
        if (clz.getPackage() != null) {
            System.out.println("PACKAGE————" + clz.getPackage().getName());
        }

        System.out.println("IS_INTERFACE————" + clz.isInterface());
        System.out.println("IS_ARRAY————" + clz.isArray());
        System.out.println("IS_PRIMITIVE————" + clz.isPrimitive());
        System.out.println("IS_ANNOTATION————" + clz.isAnnotation());
    }
}
