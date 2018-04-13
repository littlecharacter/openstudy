package com.lc.javase.thread.interrupt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class InterruptStudy {
    public void isInterrupt() {
        new Thread(() -> System.out.println("线程中断状态:" + Thread.currentThread().isInterrupted())).start();
    }

    public Thread interrupt(CountDownLatch latch) {
        Thread t = new Thread(() -> {
            System.out.println("初始线程状态：" + Thread.currentThread().isInterrupted());
            while(true) {
                try {
                    latch.countDown();
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("线程正常执行!");
                } catch (InterruptedException e) {
                    System.out.println("Thread().interrupt()前线程状态：" + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();
                    System.out.println("Thread().interrupt()后线程状态：" + Thread.currentThread().isInterrupted());
                    System.out.println("Thread().interrupt()前线程状态：" + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();
                    System.out.println("Thread().interrupt()后线程状态：" + Thread.currentThread().isInterrupted());
                    System.out.println("Thread.interrupted()前线程状态：" + Thread.interrupted());
                    System.out.println("Thread.interrupted()后线程状态：" + Thread.currentThread().isInterrupted());
                    System.out.println("Thread.interrupted()前线程状态：" + Thread.interrupted());
                    System.out.println("Thread.interrupted()后线程状态：" + Thread.currentThread().isInterrupted());
                }
            }
        });
        t.start();
        return t;
    }
}
