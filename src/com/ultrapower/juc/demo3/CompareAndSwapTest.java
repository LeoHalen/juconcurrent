package com.ultrapower.juc.demo3;

/**
 * @Description: 模拟CAS算法
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/416:39
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class CompareAndSwapTest {

    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    int expectedValue = cas.get();
                    int newValue = (int)(Math.random() * 101);
                    boolean b = cas.compareAndSet(expectedValue, newValue);
                    System.out.println(expectedValue + ":" + newValue + ":"+ b);
                }
            }).start();
        }
    }
}

class CompareAndSwap {
    private int value;

    //获取内存值
    public synchronized int get() {
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;

        if (oldValue == expectedValue) {
            this.value = newValue;
        }

        return oldValue;
    }

    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}
