package com.masq.basic.generosity;

/**
 * @title GenericResult
 * @Author masq
 * @Date: 2021/9/3 上午10:17
 * @Version 1.0
 */
public class NumberGenericResult {

    public void printNumber(SimpleTypeGenerosity<Number> generosity) {
        Number first = generosity.getFirst();
        Number second = generosity.getSecond();
        System.out.println("first=" + first + "--second=" + second);
    }


}
