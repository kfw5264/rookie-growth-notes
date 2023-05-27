package com.masq.exam.rt;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个生产者消费者组成的系统。
 * 至少需要2个以上消费者
 * 需要干净的代码风格，命名，缩进，注释。考虑边界处理，多线程编程，
 * 最高效率处理问题
 * 生产者生产1~10000 个数字
 * 消费者获得数字后，需要随机处理一段时间后输出数字【程序中sleep一段时间来模拟】
 * 整个系统最终要最高效率且顺序处理完成所有任务。【注意需要顺序完成数字输出】
 * 代码中可以撰写相关注释表达对于题目的一些思考和假设
 * @author KangFawei
 */
public class ProducerAndConsumer {
    public static void main(String[] args) throws InterruptedException {
        PutAndGetUtil util = new PutAndGetUtil();
        // 10个消费者
        int consumerCount = 10;

        // 生产者生产
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                util.put(i + 1);
            }
        }).start();

        Thread[] threads = new Thread[consumerCount];
        // 消费者线程
        for (int i = 0; i < consumerCount; i++) {
            threads[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "消费了" + util.get());
                try {
                    TimeUnit.SECONDS.sleep((long) (Math.random() * 5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "t" + i);
        }

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }
    }
}


class PutAndGetUtil {
        final Lock lock  = new ReentrantLock();
        final Condition putCondition = lock.newCondition();
        final Condition getCondition = lock.newCondition();
        final int[] nums = new int[10];
        int putIndex, getIndex, count;

    public void put(int num) {
        lock.lock();
        try {
            // 如果count跟数组大小相同，说明已经存满了，此时停止放入线程
            while (count == nums.length) {
                putCondition.await();
            }
            nums[putIndex] = num;
            // 如果存到最后一个那就从第一个开始
            if(++putIndex == nums.length) {
                putIndex = 0;
            }
            count ++;
            // 存入一个之后说明库里有数据了，然后唤醒消费者线程
            getCondition.signal();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object get() {
        int num = 0;
        lock.lock();
        // 如果count为零，说明数组中已经取完了，此时暂停取线程
        try {
            while(count == 0) {
                getCondition.await();
            }
            num = nums[getIndex];
            if(++getIndex == nums.length) {
                getIndex = 0;
            }
            count--;

            putCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return num;
    }
}