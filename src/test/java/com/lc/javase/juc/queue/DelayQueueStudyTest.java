package com.lc.javase.juc.queue;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class DelayQueueStudyTest {
    private DelayQueueStudy delayQueue = new DelayQueueStudy();

    @Test
    public void testWork() throws Exception {
        delayQueue.work();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }
}