package com.lc.javase.juc.thread.pool;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorStudy {//见名知意

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor se = new ScheduledThreadPoolExecutor(4);
        se.scheduleAtFixedRate(() -> {
            System.out.println(new Date());
            System.out.println("task-1:run...");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 3, TimeUnit.SECONDS);
        se.scheduleWithFixedDelay(() -> {
            System.out.println(new Date());
            System.out.println("task-2:run...");
        }, 1, 3, TimeUnit.SECONDS);
        se.schedule(() -> {
            System.out.println(new Date());
            System.out.println("task-3:run...");
        }, 1000, TimeUnit.MILLISECONDS);
        se.schedule(() -> {
            System.out.println("task-4:run...");
            throw new RuntimeException();
        }, 2000, TimeUnit.MILLISECONDS);

        // se.shutdown();
        // se.shutdownNow();
    }
}
