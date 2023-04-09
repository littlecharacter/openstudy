package com.lc.javase.juc.synutil;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CyclicBarrierStudyTest {
    private CyclicBarrierStudy cyclicBarrier = new CyclicBarrierStudy();

    @Test
    public void testWork() throws Exception {
        cyclicBarrier.work();
        TimeUnit.MILLISECONDS.sleep(100000L);
    }
}