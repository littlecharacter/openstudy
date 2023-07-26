package com.lc.javase.juc.thread.pool.factory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class ThreadPoolFactory {
    private ThreadPoolFactory() {}

    private static volatile ExecutorService xxExecutorService;
    private static volatile ExecutorService yyExecutorService;

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
                        new TThreadFactory("有意义的线程名称"),
                        (r,executor)->{
                            discardTaskNum.add(1);
                            System.out.println("XxThreadPool队列足够大,任务被丢弃,丢弃总任务数:" + discardTaskNum);
                        });
            }
        }
        return xxExecutorService;
    }

    public static ExecutorService getYyThreadPool(){
        if(yyExecutorService != null){
            return yyExecutorService;
        }
        synchronized (ThreadPoolFactory.class) {
            if(yyExecutorService == null){
                yyExecutorService = new YyThreadPoolExecutor(
                        5,
                        5,
                        0L,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<>(CAPACITY),
                        new TThreadFactory("有意义的线程名称"),
                        (r,executor)->{
                            discardTaskNum.add(1);
                            System.out.println("YyThreadPool队列足够大,任务被丢弃,丢弃总任务数:" + discardTaskNum);
                        });
            }
        }
        return yyExecutorService;
    }

    static class YyThreadPoolExecutor extends ThreadPoolExecutor {

        public YyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public YyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public YyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public YyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        /**
         * 如果任务种有异常没有catch，抛给了线程池，用这个方法处理
         * @param r
         * @param t
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println(new Thread(r).getName() + "异常：");
            t.printStackTrace();
        }
    }
}
