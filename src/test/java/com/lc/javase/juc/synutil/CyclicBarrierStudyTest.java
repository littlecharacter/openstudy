package com.lc.javase.juc.synutil;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CyclicBarrierStudyTest {
    private CyclicBarrierStudy cyclicBarrier = new CyclicBarrierStudy();

    @Test
    public void testRace() throws Exception{
        cyclicBarrier.race();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }
}