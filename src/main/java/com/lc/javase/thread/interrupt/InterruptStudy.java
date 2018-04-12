package com.lc.javase.thread.interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptStudy {
    public void isInterrupt() {
        new Thread(() -> System.out.println("线程中断状态:" + Thread.currentThread().isInterrupted())).start();
    }

    public Thread interrupt() {
        Thread t = new Thread(() -> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("线程正常执行!");
                } catch (InterruptedException e) {
                    System.out.println("线程中断状态:" + Thread.currentThread().isInterrupted());
                }
            }
        });
        t.start();
        return t;
    }
}
