package com.masq.basic.exception;

public class Demo {

    public static void main(String[] args) {
        System.out.println(getInt());
    }

    private static int getInt() {
        try {
            int i = 0;

            // 终止运行的情况下finally不会被执行
//            System.exit(1);
            return 10/i;
        } catch (Exception e) {
            System.out.println("ERROR");
            return 0;
        } finally {
            System.out.println("FINALLY");
        }

    }
}
