package com.lc.javase.thread.synutil;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CountDownLatchStudyTest {
    private CountDownLatchStudy countDownLatch = new CountDownLatchStudy();

    @Test
    public void testWork() throws Exception {
        countDownLatch.work();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }

}