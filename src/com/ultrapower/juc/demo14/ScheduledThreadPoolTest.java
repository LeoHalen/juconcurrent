package com.ultrapower.juc.demo14;

import java.util.Random;
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
public class ScheduledThreadPoolTest {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 5; i++) {

            Future<Integer> result = pool.schedule(() -> {
                int num = new Random().nextInt(100);
                System.out.println(Thread.currentThread().getName() + ":" + num);
                return num;
            }, 1, TimeUnit.SECONDS);

            System.out.println(result.get());
        }

        pool.shutdown();
    }
}
