package com.lc.javase.thread.interrupt;

import org.junit.Test;

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
        Thread t = interruptStudy.interrupt();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(t.isInterrupted());
        t.interrupt();
        System.out.println(t.isInterrupted());
        TimeUnit.SECONDS.sleep(30);
    }
}