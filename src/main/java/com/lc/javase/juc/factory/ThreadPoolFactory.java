package com.lc.javase.juc.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class ThreadPoolFactory {
    private ThreadPoolFactory() {}

    private static volatile ExecutorService xxExecutorService;

    private static final Integer CAPACITY = 3500;

    private static LongAdder discardTaskNum = new LongAdder();

    public static ExecutorService getXxThreadPool(){
        if(xxExecutorService != null){
            return xxExecutorService;
        }
        synchronized (ThreadPoolFactory.class) {
            if(xxExecutorService == null){
                xxExecutorService = new ThreadPoolExecutor(
                        5,
                        5,
                        0L,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<>(CAPACITY),
                        new CustomThreadFactory("有意义的线程名称"),
                        (r,executor)->{
                            discardTaskNum.add(1);
                            System.out.println("XxThreadPool队列足够大,任务被丢弃,丢弃总任务数:" + discardTaskNum);
                        });
            }
        }
        return xxExecutorService;
    }

}
