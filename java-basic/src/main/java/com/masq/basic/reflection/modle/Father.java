package com.masq.basic.reflection.modle;

/**
 * @title Father
 * @Author masq
 * @Date: 2021/8/19 上午9:14
 * @Version 1.0
 */
public class Father {

    static {
        System.out.println("father static block...");
    }

    {
        System.out.println("father block...");
    }

    String field;
    private String fatherPrivateField;
    public String fatherPublicField;
    protected String fatherProtectedField;
    private static final String FATHER_CONSTANT_FIELD = "ABC";

    public Father() {
        this("parm1", 1);
    }

    private Father(String parameter1, int parameter2) {}

    public class PublicFatherInnerField {}

    private class PrivateFatherInnerField {}

    public static void fatherPublicStaticMethodNoReturn() {}

    private static void fatherPrivateStaticMethodNoReturn() {}

    static void fatherStaticMethodNoReturn() {}

    protected static void fatherProtectedStaticMethodNoReturn() {}

    public String fatherPublicMethodAndReturn() {
        return null;
    }

    private String fatherPrivateMethodAndReturn() {
        return null;
    }

    String fatherMethodAndReturn() {
        return null;
    }

    protected String fatherProtectedMethodAndReturn() {
        return null;
    }
}
