package com.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by pjy on 2017/5/3.
 */

public class ThreadPoolDemo03 {

    public static void main(String[] args) {

        //有计划的线程池对象(此接口继承ExecutorService)
        ScheduledExecutorService executorService=
        Executors.newScheduledThreadPool(2);

        //5秒以后执行一个任务
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        },5,TimeUnit.SECONDS);

        //如下方法了解

     /*  executorService.scheduleAtFixedRate(
                task,//要执行的任务
                initialDelay,//初始延迟时间
                period,//每次任务执行的间隔时间(上一次任务有可能还没有结束)
                timeUnit);//时间单位*/

       /* executorService.scheduleWithFixedDelay(
                task,//要执行的任务
                initialDelay,//初始延迟时间
                delay,//上一次任务执行时间+delay后，启动下一次任务
                timeUnit//时间单位
        );*/


    }
}
