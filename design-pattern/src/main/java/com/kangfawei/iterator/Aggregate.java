package com.kangfawei.iterator;

public interface Aggregate {
    void add(Object obj);
    void remove(Object obj);
    public Iterator getIterator();
}
