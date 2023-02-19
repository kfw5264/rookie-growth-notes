package com.masq.basic.generosity;

/**
 * @title SimpleTypeGenerosity
 * @Author masq
 * @Date: 2021/9/3 上午9:53
 * @Version 1.0
 */
public class SimpleTypeGenerosity<T> {
    private T first;
    private T second;

    public SimpleTypeGenerosity() {
    }


    public SimpleTypeGenerosity(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }


    public T getSecond() {
        return second;
    }

    public static <T> SimpleTypeGenerosity<T> create(T first, T second) {
        return new SimpleTypeGenerosity<T>(first, second);
    }

}
