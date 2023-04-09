package com.lc.javase.juc.pattern;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ProducerConsumerPatternTest {
    private ProducerConsumerPattern pattern = new ProducerConsumerPattern();

    @Test
    public void testWorkWithNotify() throws Exception {
        pattern.workWithNotify();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }

    @Test
    public void testWorkWithBlockingQueue() throws Exception {
        pattern.workWithBlockingQueue();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }

    @Test
    public void testWorkWithPipedIO() throws Exception {
        pattern.workWithPipedIO();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }
}