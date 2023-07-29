package com.lc.javase.juc.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author gujixian
 * @since 2023/7/29
 */
public class PrintAB {
    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[2];
        threads[0] = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println("A");
                LockSupport.unpark(threads[1]);
                LockSupport.park();
            }
        });
        threads[1] = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                LockSupport.park();
                System.out.println("B");
                LockSupport.unpark(threads[0]);
            }
        });
        threads[0].start();
        threads[1].start();
    }
}
