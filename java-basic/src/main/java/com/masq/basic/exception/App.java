package com.masq.basic.exception;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @title App
 * @Author masq
 * @Date: 2021/8/30 上午10:39
 * @Version 1.0
 */
public class App {
    public static void main(String[] args) throws Exception {
//        tryCatch();
//        tryWithResource();
//        try {
//            getExceptionWithCatch();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        conversionException();
//        exceptionShielding();
//        getAllException();
//        getException();
        testApiException();
    }

    private static void testApiException() {
        throw new ApiException(ErrorCode.NO_AUTHENTICATION);
    }

    private static void getException() throws Exception {
        throw new Exception("测试", null);
    }

    public static void getAllException() throws Exception {
        Exception origin = null;
        try {
            System.out.println(1/0);
        } catch (Exception e) {
            System.out.println("catch");
            origin = e;
        } finally {
            System.out.println("finally");
            NullPointerException exception = new NullPointerException();
            if (null != origin) {
                origin.addSuppressed(exception);
            }
            throw origin;
        }
    }

    public static void exceptionShielding() {
        try {
            System.out.println(1/0);
        } catch (Exception e) {
            System.out.println("catch");
            throw new ArithmeticException();
        } finally {
            System.out.println("finally");
            throw new NullPointerException();
        }
    }

    public static void conversionException() {
        try {
            getExceptionWithCatch();
        } catch (Exception e) {
//            throw new IllegalArgumentException();
            throw new IllegalArgumentException(e);
        }
    }

    public static void getExceptionWithCatch() {
        throwException();
    }

    public static void throwException() {
        throw new NullPointerException();
    }

    public static void tryCatch() {
        InputStream is = null;

        try {
            is = new FileInputStream("/home/masq/test.txt");
            int data = 0;
            while ((data = is.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void tryWithResource () {
        try (InputStream is = new FileInputStream("/home/masq/test.txt")) {
            int data = 0;
            while ((data = is.read()) != -1) {
                System.out.print((char) data);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
