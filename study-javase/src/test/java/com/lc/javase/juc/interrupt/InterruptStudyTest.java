package com.lc.javase.juc.interrupt;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class InterruptStudyTest {
    private InterruptStudy interruptStudy = new InterruptStudy();

    @Test
    public void testIsInterrupt() throws Exception {
        interruptStudy.isInterrupt();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void testInterrupt() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread t = interruptStudy.interrupt(latch);
        latch.await();
        t.interrupt();
        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void interruptTest() throws Exception {
        Thread t = new Thread(() -> {
            System.out.println("线程一启动");
            while (!Thread.interrupted()) {
                System.out.println("线程一执行");
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("线程一中断?" +Thread.currentThread().isInterrupted()+ ".");
                    // 设置线程的中断转态为true，控制跳出while循环
                    Thread.currentThread().interrupt();
                    System.out.println("线程一中断?" +Thread.currentThread().isInterrupted()+ ".");
                }
            }
            System.out.println("线程一结束");
        });
        t.start();
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}