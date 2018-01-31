package com.lc.javase.util;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class QueueStudyTest {
    QueueStudy queueStudy = new QueueStudy();

    @Test
    public void testCycleQueue() {
        queueStudy.cycleQueueToProducerAndConsumerModel();
        try {
            TimeUnit.MILLISECONDS.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCircleQueue() {
        queueStudy.circleQueueToProducerAndConsumerModel();
        try {
            TimeUnit.MILLISECONDS.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}