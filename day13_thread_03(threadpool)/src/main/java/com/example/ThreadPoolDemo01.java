package com.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolDemo01 {

    /*池中核心线程数*/
    public static final int  CORE_POOL_SIZE=1;
    /**池中最多线程数*/
    public static final int MAXIMUM_POOL_SIZE=2;
    /**池中非核心线程的空闲时间*/
    public static final int KEEP_ALIVE_SECONDS=1;
    /**时间单位*/
    public static final TimeUnit KEEP_ALIVE_TIME_UNIT=TimeUnit.SECONDS;
    /**阻塞式任务队列*/
    public static final BlockingQueue<Runnable>  sPoolWorkQueue=
            new ArrayBlockingQueue<Runnable>(3);
    public static void main(String[] args) {

        //1.构建线程池对象(此时池中并没有线程)
        ThreadPoolExecutor poolExecutor=
                new ThreadPoolExecutor(
                        CORE_POOL_SIZE,
                        MAXIMUM_POOL_SIZE,
                        KEEP_ALIVE_SECONDS,
                        KEEP_ALIVE_TIME_UNIT,
                        sPoolWorkQueue);
        //2.执行任务
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String tname=Thread.currentThread().getName();
                System.out.println(tname+"-task-01 start");
                try{Thread.sleep(5000);}catch(Exception e){}
                System.out.println(tname+"-task-01 end");
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String tname=Thread.currentThread().getName();
                System.out.println(tname+"-task-02 start");
                try{Thread.sleep(5000);}catch(Exception e){}
                System.out.println(tname+"-task-02 end");
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String tname=Thread.currentThread().getName();
                System.out.println(tname+"-task-03 start");
                try{Thread.sleep(5000);}catch(Exception e){}
                System.out.println(tname+"-task-03 end");
            }
        });

        //当池对象不使用了
        poolExecutor.shutdown();//需要等到所有任务执行结束
       // poolExecutor.shutdownNow();//立刻关闭，可能有些任务还没有结束
    }
}
