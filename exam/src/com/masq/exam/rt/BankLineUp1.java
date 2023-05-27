package com.masq.exam.rt;


import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟一个银行排队的场景，银行有3个窗口，采取排队的策略，是来了一个客户就直接排到一个随机的队列，
 * 生成一个号码（取号），每个客户来了有不同的处理时间（可以随机产生），现在用代码模拟一下这个场景，
 * 输出每个用户等待的时间和总处理时间。
 * @author masq
 */
public class BankLineUp1 {


    public static void main(String[] args) throws InterruptedException {
        // 假设顾客有20个
        int customerCount = 20;
        // 窗口有三个
        int windowsCount = 3;

        CountDownLatch latch = new CountDownLatch(windowsCount);
        // 队列标识顾客在排队
        Queue<Customer1> queue = new LinkedBlockingQueue<>(customerCount);
        for (int i = 0; i < customerCount; i++) {
            queue.offer(new Customer1(i + 1));
        }
        // 处理开始时间
        long time = System.currentTimeMillis();

        for (int i = 0; i < windowsCount; i++) {
            String name = i + 1 + "号窗口";
            new Thread(new Window1(name, queue, latch)).start();
        }
        // 等待所有窗口的业务完成之后计算总处理时间
        latch.await();
        System.out.println("总处理时间" + (System.currentTimeMillis() - time) + "ms");
    }
}


class Window1 implements Runnable {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final String name;
    private final Queue<Customer1> queue;
    private final CountDownLatch latch;

    public Window1(String name, Queue<Customer1> queue, CountDownLatch latch) {
        this.name = name;
        this.queue = queue;
        this.latch = latch;
    }

    @Override
    public void run() {
        while(!queue.isEmpty()) {
            // 叫号
            int code = counter.incrementAndGet();
            Customer1 customer = queue.poll();

            if (customer != null) {
                try {
                    System.out.println("请" + customer.getCode() + "号顾客到" + name + "办理业务！");
                    // 办理业务
                    customer.business();
                    System.out.println(customer.getCode() + "客户办理完成");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // 每个线程执行完成之后递减
        latch.countDown();
    }
}

class Customer1 {

    private final int code;
    private final long startWaitTime;

    public Customer1(int code) {
        this.code = code;
        startWaitTime = System.currentTimeMillis();
    }

    public void business() throws InterruptedException {
        // 从创建对象到办理业务的时间为等待时间
        System.out.println(code + "号客户等待时间为" + (System.currentTimeMillis() - startWaitTime) + "ms");
        TimeUnit.SECONDS.sleep((long) (Math.random() * 5));
    }

    public int getCode() {
        return code;
    }
}



