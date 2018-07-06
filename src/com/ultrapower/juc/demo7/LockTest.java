package com.ultrapower.juc.demo7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 *    一、用于解决多线程安全问题的方式：
 *      synchronized：隐式锁
 *      1、同步代码快
 *      2、同步方法
 *      jdk 1.5 后：
 *      3、同步锁Lock
 *      注意：是一个显示锁，需要通过lock()方法上锁，必须通过unlock()方法进行释放
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/614:00
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class LockTest {

    public static void main(String[] args) {
        Ticket ticket  = new Ticket();

        new Thread(ticket,"1号窗口").start();
        new Thread(ticket,"2号窗口").start();
        new Thread(ticket,"3号窗口").start();
    }
}

class Ticket implements Runnable {

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {

            lock.lock();//上锁

            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(Thread.currentThread().getName() + "完成售票，余票为：" + --tick);
                }
            }finally {
                lock.unlock();//释放锁
            }
        }
    }
}