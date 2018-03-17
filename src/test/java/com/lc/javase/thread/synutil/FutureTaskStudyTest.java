package com.lc.javase.thread.synutil;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class FutureTaskStudyTest {
    private FutureTaskStudy futureTask = new FutureTaskStudy();

    @Test
    public void testWork() throws Exception {
        futureTask.work();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }
}