package com.masq.basic.reflection.modle;

import com.masq.basic.reflection.modle.Father;

import java.io.Serializable;

/**
 * @title Son
 * @Author masq
 * @Date: 2021/8/19 上午9:14
 * @Version 1.0
 */
public class Son extends Father implements Serializable {

    static {
        System.out.println("son static block...");
    }

    {
        System.out.println("son block...");
    }

    String field;
    private String sonPrivateField;
    public String sonPublicField;
    protected String sonProtectedField;
    private static final String FATHER_CONSTANT_FIELD = "ABC";

    public Son() {
        this("parm1", 1);
    }

    private Son(String parameter1, int parameter2) {
        System.out.println(parameter1 + "--" + parameter2);
    }

    public class PublicSonInnerField {}

    private class PrivateSonInnerField {}

    public static void sonPublicStaticMethodNoReturn() {}

    private static void sonPrivateStaticMethodNoReturn() {}

    static void sonStaticMethodNoReturn() {}

    protected static void sonProtectedStaticMethodNoReturn() {}

    public String sonPublicMethodAndReturn() {
        return null;
    }

    private String sonPrivateMethodAndReturn() {
        return null;
    }

    String sonMethodAndReturn() {
        return null;
    }

    protected String sonProtectedMethodAndReturn() {
        return null;
    }

    public  final void sonPublicFinalMethodNoParam() {}
}
