package com.masq.basic.generosity;

/**
 * @title GenericType
 * @Author masq
 * @Date: 2021/9/2 下午4:19
 * @Version 1.0
 */
public class TenSizeArray<E> {
    private Object[] array = new Object[10];
    private int curIndex = 0;

    public E add(E e) {
        if (curIndex >= array.length) {
            throw new ArrayIndexOutOfBoundsException("元素超出规定的10个");
        }
        array[curIndex] = e;
        curIndex ++;
        return e;
    }

    public E get(int index) {
        if(index >= array.length) {
            throw new ArrayIndexOutOfBoundsException("Index超出范围：" + index );
        } else if (index > curIndex) {
            return null;
        }
        return (E)array[index];
    }

    public static <E> TenSizeArray<E> create(E... elements) {
        TenSizeArray<E> tenSizeArray = new TenSizeArray<>();
        for (E element : elements) {
            tenSizeArray.add(element);
        }
        return tenSizeArray;
    }
}
