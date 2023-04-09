package com.lc.javase.juc.factory;

import org.junit.Test;

import java.util.concurrent.ExecutorService;

public class ThreadPoolFactoryTest {
    @Test
    public void testGetXxThreadPool() throws Exception {
        ExecutorService executor = ThreadPoolFactory.getXxThreadPool();
        executor.submit(() -> System.out.println("hello"));
        executor.shutdown();
    }
}