package com.lc.javase.juc.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gujixian
 * @since 2023/7/29
 */
public class PrintAB {
    public static void main(String[] args) throws Exception {
        // Thread[] threads = new Thread[2];
        // threads[0] = new Thread(() -> {
        //     for (int i = 0; i < 50; i++) {
        //         System.out.println("A");
        //         LockSupport.unpark(threads[1]);
        //         LockSupport.park();
        //     }
        // });
        // threads[1] = new Thread(() -> {
        //     for (int i = 0; i < 50; i++) {
        //         LockSupport.park();
        //         System.out.println("B");
        //         LockSupport.unpark(threads[0]);
        //     }
        // });
        // threads[0].start();
        // threads[1].start();
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(0);
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    semaphoreA.acquire(1);
                    System.out.println("A");
                    semaphoreB.release(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    semaphoreB.acquire(1);
                    System.out.println("B");
                    semaphoreA.release(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
