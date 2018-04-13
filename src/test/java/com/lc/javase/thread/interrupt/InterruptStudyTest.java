package com.lc.javase.thread.interrupt;

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
}