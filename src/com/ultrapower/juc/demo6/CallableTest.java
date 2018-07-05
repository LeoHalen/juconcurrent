package com.ultrapower.juc.demo6;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 *      一、创建执行线程的方式三：实现Callable接口。相较于实现Runnable
 *          接口的方式，方法可以有返回值，并且可以抛出异常。
 *      二、 执行Callable方式，需要FutrureTask实现类的支持，用于接收运算结果。FutureTask是Futrue接口的实现类
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/7/515:23
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class CallableTest {

    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();

        //1、执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果。
        FutureTask<Integer> result = new FutureTask<>(demo);

        new Thread(result).start();


        //2、接受线程运算后的结果
        try {
            Integer sum = result.get();//FutureTask也可用于闭锁，当此线程没获取到值时，主线程一直等待。
            System.out.println(sum);
            System.out.println("----------------------------------");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ThreadDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;

        for (int i = 0; i <= 10000; i++) {
            sum += i;
        }
        return sum;
    }
}