package com.ultrapower.juc.demo13;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description:
 *  一、线程池
 *
 *  二、线程池的体系结构
 *      java.util.concurrent.Executor:负责线程的使用与调度的根接口
 *          |--ExcutorService 子接口：线程池的主要接口
 *              |--ThreadPoolExecutor 线程池的实现类
 *              |--ScheduledExecutorService 子接口：负责线程的调度
 *                  |--ScheduledThreadPoolExecutor：继承 ThreadPoolExecutor，实现 ScheduledExecutorService
 *  三、工具类：Executor
 *      ExecutorService newFixedThreadPool():创建固定大小的线程池
 *      ExecutorService newCachedThreadPool():缓存线程池，线程池的数量不固定，可以根据需求自动更改数量
 *      ExecutorService newSingleThreadExecutor():创建单个线程，线程池中只有一个线程实例
 *
 *      ScheduledExecutorService newScheduledThreadPool():创建固定大小的线程池,可以延时或定时的执行任务
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/914:01
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class ThreadPollTest {

    public static void main(String[] args) throws Exception {
        //1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        List<Future<Integer>> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(() -> {
                int sum = 0;

                for (int j = 0; j <= 100; j++) {
                    sum += j;
                }
                return sum;
            });

            list.add(future);
        }

        pool.shutdown();

        for (Future<Integer> future : list
             ) {
            System.out.println(future.get());
        }

        /*ThreadPoolDemo poolDemo = new ThreadPoolDemo();

        //2.为线程池中的线程分配任务
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> poolDemo.get());
//          pool.submit(poolDemo);
        }

        //3.关闭线程池
        pool.shutdown();*/
    }
}

class ThreadPoolDemo{

    private int i = 0;

    public void get() {
        while (i < 100) {
            System.out.println(Thread.currentThread().getName() + ":" + ++i);
        }
    }
}
/*
class ThreadPoolDemo implements Runnable {

    private int i = 0;

    @Override
    public void run() {
        while (i < 100) {
            System.out.println(Thread.currentThread().getName() + ":" + ++i);
        }
    }
}*/
