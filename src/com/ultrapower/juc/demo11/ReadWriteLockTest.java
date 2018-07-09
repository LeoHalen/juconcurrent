package com.ultrapower.juc.demo11;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description:
 *      1. ReadWriteLock:读写锁
 *      写写/读写 需要“互斥”
 *      读读 不需要互斥
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/910:52
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReadWriteLockDemo lockDemo = new ReadWriteLockDemo();

        new Thread(() -> lockDemo.set((int) (Math.random() * 101)), "Thread-A").start();

        for (int i = 0; i < 100; i++) {

            new Thread(() -> lockDemo.get()).start();
        }
    }
}

class ReadWriteLockDemo {

    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    //读
    public void get() {
        lock.readLock().lock();//上锁


        try {
            System.out.println(Thread.currentThread().getName() + ":执行读操作->:" + number);
        }finally {
            lock.readLock().unlock();//释放锁
        }
    }

    //写
    public void set(int number) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":执行写入操作->" + number);
            this.number = number;
        } finally {
            lock.writeLock().unlock();
        }
    }
}