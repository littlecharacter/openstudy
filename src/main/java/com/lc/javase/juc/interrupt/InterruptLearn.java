package com.lc.javase.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptLearn {
    private static volatile boolean go = true;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (go) {
                System.out.println("线程执行中...");
                Thread.yield();
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("线程被中断!");
                System.out.println("线程中断状态:" + Thread.currentThread().isInterrupted());
                System.out.println("线程中断状态:" + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
        });
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        System.out.println(t1.isInterrupted());
        go = false;
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("**********************线程-1测试结束**********************");
        Lock lock = new ReentrantLock();
        lock.lock();
        Thread t2 = new Thread(() -> {
            lock.lock();
            System.out.println("线程执行中...");
            Thread.interrupted();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                System.out.println("线程被中断!");
                System.out.println("线程中断状态:" + Thread.currentThread().isInterrupted());
                System.out.println("线程中断状态:" + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
            System.out.println("线程正常运行结束!");
        });
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
        assert t2.isInterrupted();
        t2.interrupt();
        assert t2.isInterrupted();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
}
