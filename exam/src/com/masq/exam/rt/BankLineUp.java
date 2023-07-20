package com.masq.exam.rt;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟一个银行排队的场景，银行有3个窗口，采取排队的策略，是来了一个客户就直接排到一个随机的队列，
 * 生成一个号码（取号），每个客户来了有不同的处理时间（可以随机产生），现在用代码模拟一下这个场景，
 * 输出每个用户等待的时间和总处理时间。
 * @author masq
 */
public class BankLineUp {

    /*
     * 逻辑：三个窗口，每个窗口对应一个队列
     */

    public static void main(String[] args) {
        // 三个队列
        Queue<Customer> queue1 = new LinkedBlockingQueue<>();
        Queue<Customer> queue2 = new LinkedBlockingQueue<>();
        Queue<Customer> queue3 = new LinkedBlockingQueue<>();

        // 创建一个线程生成客户，并随机排队
        new Thread(new CustomerGenerator(queue1, queue2, queue3)).start();

        // 三个窗口线程办理业务
        new Thread(new Window(queue1), "窗口1").start();
        new Thread(new Window(queue2), "窗口2").start();
        new Thread(new Window(queue3), "窗口3").start();
    }


}

class CustomerGenerator implements Runnable {

    private final Queue<Customer> queue1;
    private final Queue<Customer> queue2;
    private final Queue<Customer> queue3;

    public CustomerGenerator(Queue<Customer> queue1, Queue<Customer> queue2, Queue<Customer> queue3) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.queue3 = queue3;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            i++;
            // 客户随机排队
            double value = Math.random() * 3;
            if (value >= 0 && value < 1) {
                queue1.offer(new Customer(i));
            }
            if (value >= 1 && value < 2) {
                queue2.offer(new Customer(i));
            }
            if (value >= 2 && value < 3) {
                queue3.offer(new Customer(i));
            }
            // 等待随机时间，标识客户进入的时间间隔
            try {
                TimeUnit.SECONDS.sleep((long) (Math.random() * 5));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Window implements Runnable {

    private final Queue<Customer> queue;

    public Window(Queue<Customer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            while(!queue.isEmpty()) {
                Customer customer = queue.poll();
                try {
                    System.out.println(Thread.currentThread().getName() + "正在办理" + customer.getCode() + "号用户业务");
                    customer.business();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

class Customer {

    private final int code;
    private final long startTime;
    public Customer(int code) {
        this.code = code;
        startTime = System.currentTimeMillis();
    }

    /**
     * 客户办理的业务
     * @throws InterruptedException -
     */
    public void business() throws InterruptedException {
        System.out.println(code + "号客户等待时间：" + (System.currentTimeMillis() - startTime));
        // 办理业务
        TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
        System.out.println(code + "号客户总办理时间：" + (System.currentTimeMillis() - startTime));
    }

    /**
     * 获取客户编号
     * @return code
     */
    public int getCode() {
        return code;
    }
}
