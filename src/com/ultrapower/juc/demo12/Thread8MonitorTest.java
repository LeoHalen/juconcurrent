package com.ultrapower.juc.demo12;

/**
 * @Description: 题目：判断打印的 "ont" or "two" ?
 *
 * 1.两个普通同步方法，两个线程，标准打印，打印? //one two
 * 2.在getOne()中新增Thread.sleep() 打印? //one two
 * 3.新增普通方法getThree() 打印? //three one two
 * 4.两个普通同步方法，两个NumberDemo对象 打印？ //two one
 * 5.修改getOne()为静态同步方法，一个NumberDemo对象 打印？ //two one
 * 6.修改两个方法均为静态同步方法，一个NumberDemo对象 打印？ //one two
 * 7.一个静态同步方法，一个非静态同步方法，两个NumberDemo对象 打印？ //two one
 * 8.两个静态同步方法，两个NumberDemo对象 打印？ //one two
 *
 * 线程八锁个关键：
 * ①非静态方法的锁默认为 this, 静态方法的锁为对应的Class 实例
 * ②某一个时刻内，只能有一个线程持有锁，无论几个方法
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/911:20
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class Thread8MonitorTest {

    public static void main(String[] args) {
        NumberDemo ndemo = new NumberDemo();
        NumberDemo ndemo2 = new NumberDemo();

        new Thread(() -> {
            ndemo.getOne();
        }).start();

        new Thread(() -> {
//            ndemo.getTwo();
            ndemo2.getTwo();
        }).start();

        /*new Thread(() -> {
            ndemo.getThree();
        }).start();*/
    }
}

class NumberDemo {

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }
}