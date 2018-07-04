package com.ultrapower.juc.demo2;

/**
 * 测试多线程下的变量原子性安全问题
 * 一、i++ 的原子性问题：
 *      int i = 10;
 *      i = i++;//10
 *
 *      int temp = i;
 *      i = i + 1;
 *      i = temp;
 * 二、原子变量：jdk1.5后java.util.concurrent.atomic包下提供了常用的原子变量：
 *      1.volatile 保证内存可见性
 *      2.CAS(Compare-And-Swap) 算法保证数据的原子性
 *          CAS 算法是硬件对于并发操作的共享数据的支持
 *          CAS 包含了三个操作数：
 *              内存值 V
 *              预估值 A
 *              更新值 B
 *              当且仅当 V = A, V = B.否则，将不做任何操作。
 * @ProjectName: juconcurrent
 * @Description: java类作用描述
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/3 14:46
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class AtomicTest {

    public static void main(String[] args) {
        AtomicDemo demo = new AtomicDemo();

        for (int i = 0; i < 10; i++){
            new Thread(demo).start();
        }
    }
}

class AtomicDemo implements Runnable {

    private volatile int serialNumber = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

    public int getSerialNumber() {
        return  serialNumber++;
    }
}