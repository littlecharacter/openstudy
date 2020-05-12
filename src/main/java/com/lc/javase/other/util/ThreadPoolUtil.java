package com.lc.javase.other.util;

import com.sun.org.apache.xml.internal.security.Init;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class ThreadPoolUtil {
    private static final int THREAD_POOL_NUM_5 = 5;

    private ThreadPoolUtil() {}

    private static volatile ExecutorService executorService;

    private static final Integer CAPACITY = 3500;

    private static LongAdder discardPolicyNum = new LongAdder();

    public static ExecutorService getXxThreadPool(){
        if(executorService != null){
            return executorService;
        }
        synchronized (Init.class){
            if(executorService == null){
                executorService = new ThreadPoolExecutor(THREAD_POOL_NUM_5, THREAD_POOL_NUM_5,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<>(CAPACITY),
                        new CustomThreadFactory("handle_deposit_client"),
                        (r,executor)->{
                            discardPolicyNum.add(1);
                            System.out.println("AdjustAccountDepositTask 队列足够大,任务被丢弃,丢弃总任务数:" + discardPolicyNum);
                        });
            }
        }
        return executorService;
    }
}
