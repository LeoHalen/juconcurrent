package com.ultrapower.juc.demo5;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: 闭锁，在完成某些运算时，只有其他所有线程运算全部完成，
 *      运算才继续执行。
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/511:22
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(5);
        LatchDemo latchDemo = new LatchDemo(latch);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(latchDemo).start();
        }

        //主线程等待闭锁结束再执行
        try {
            latch.await();
        } catch (InterruptedException e) {
        }

        long endTime = System.currentTimeMillis();

        System.out.println("耗费时间："+(endTime - startTime));
    }
}

class LatchDemo implements Runnable {

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
//                System.out.println("线程" + Thread.currentThread().getName() + "打印:" + i);
                    }
                }
            }finally {
                latch.countDown();
            }
        }

    }
}