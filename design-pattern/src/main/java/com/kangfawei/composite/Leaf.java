package com.kangfawei.composite;

public class Leaf implements Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void add(Component component) {}

    @Override
    public void remove(Component component) {}

    @Override
    public Component getChild(int i) {
        return null;
    }

    @Override
    public void operate() {
        System.out.println("树叶"+name+"被访问");
    }
}
