package com.lc.javase.juc.synutil;

import com.lc.javase.juc.aqs.synutil.CountDownLatchStudy;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchStudyTest {
    private final CountDownLatchStudy countDownLatch = new CountDownLatchStudy();

    @Test
    public void testWork() throws Exception {
        countDownLatch.work();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }

    @Test
    public void testConcurrent() throws Exception {
        final int THREAD_NUM = 10;
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(THREAD_NUM);

        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        System.out.println(Thread.currentThread().getName() + ":start!");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println(Thread.currentThread().getName() + ":over!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        System.out.println("Time:" + (end - start));
    }
}