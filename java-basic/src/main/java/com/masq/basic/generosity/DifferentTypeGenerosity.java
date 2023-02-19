package com.masq.basic.generosity;

/**
 * @title DifferentTypeGenerosity
 * @Author masq
 * @Date: 2021/9/3 上午9:48
 * @Version 1.0
 */
public class DifferentTypeGenerosity<K, V> {
    private K key;
    private V value;

    public DifferentTypeGenerosity() {
    }

    public DifferentTypeGenerosity(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
