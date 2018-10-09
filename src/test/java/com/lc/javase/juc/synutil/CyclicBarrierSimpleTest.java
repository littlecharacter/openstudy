package com.lc.javase.juc.synutil;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CyclicBarrierSimpleTest {
    private CyclicBarrierSimple cyclicBarrier = new CyclicBarrierSimple();
    @Test
    public void testWork() throws Exception {
        cyclicBarrier.work();
        TimeUnit.MILLISECONDS.sleep(100000L);
    }
}