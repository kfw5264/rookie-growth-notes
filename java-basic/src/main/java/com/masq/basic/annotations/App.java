package com.masq.basic.annotations;

import java.lang.reflect.Field;

/**
 * @title App
 * @Author masq
 * @Date: 2021/8/30 下午4:47
 * @Version 1.0
 */
public class App {

    public static void main(String[] args) throws IllegalAccessException {
        Person person = new Person();
        person.setUsername("a");
        person.setPassword("123456");
        person.setEmail("a@xxx.com");
        person.setPhone("131222255556");
        check(person);
    }

    private static void check (Person person) throws IllegalAccessException {
        Class<? extends Person> personClass = person.getClass();
        Field[] declaredFields = personClass.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Range range = field.getAnnotation(Range.class);
            if (null != range) {
                Object o = field.get(person);
                if (o instanceof String) {
                    String value = (String) o;
                    if (value.length() < range.min() || value.length() > range.max()) {
                        throw new IllegalArgumentException("Invalid field: " + field.getName());
                    }
                }
            }
        }
    }
}
