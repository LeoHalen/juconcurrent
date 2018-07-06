package com.ultrapower.juc.demo9;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 生产者消费者案例(同步锁实现 等待唤醒机制)
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/615:25
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class ProductorAndConsumerForLockTest {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(productor, "生产者 A").start();
        new Thread(consumer, "消费者 B").start();
        //再创建两个线程，验证出现虚假唤醒问题，解决方法——>while总是循环中
        new Thread(productor, "生产者 A").start();
        new Thread(consumer, "消费者 B").start();
    }

}
class Clerk {
    private int product = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    //进货
    public void get() {

        lock.lock();

        try {
            while (product >= 1) {//为了避免虚假唤醒问题，应该总是使用在循环中
                System.out.println("产品已满！");

                //如果生产满了，生产线程进行阻塞状态，等待消费...
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }

            //去掉else可以解决在最后一次循环中，下面代码唤醒无限等待的生产者，
            System.out.println(Thread.currentThread().getName() + ":" + ++product);
            //如果产品不满，唤醒生产的线程
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    //卖货
    public void sale() {

        lock.lock();

        try {
            while (product <= 0) {//为了避免虚假唤醒问题，应该总是使用在循环中
                System.out.println("缺货！");

                //如果消费完了，消费线程进行阻塞状态，等待生产...
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }

            System.out.println(Thread.currentThread().getName() + ":" + --product);
            //如果有产品了，唤醒消费线程
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 生产者
 */
class Productor implements Runnable{

    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            clerk.get();
        }
    }
}
/**
 * 消费者
 */
class Consumer implements Runnable{

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
