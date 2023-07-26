package com.lc.javase.juc.thread.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptStudy {
    private static volatile boolean go = true;
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            while (go) {
                // 测试点一：运行中的线程是否会被中断
                System.out.println("线程执行中...");
                Thread.yield(); // 线程只是让出cpu（running -> runnable），没什么其他影响
                // 测试点二：blocked的线程是否会被中断
                // try {
                //     System.in.read(new byte[1024]);
                // } catch (IOException e) {
                //     throw new RuntimeException(e);
                // }
            }
            try {
                // 测试点三：timed_waiting中的线程是否会被中断
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                // 一旦抛出异常，线程状态就会被设置为false
                System.out.println("线程被中断!");
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
            System.out.println("线程等待获取锁...");
            try {
                // 测试点四：
                lock.lock();
                lock.lockInterruptibly();
                System.out.println("线程开始执行中...");
                //Thread.interrupted();
                System.out.println("线程正常运行结束!");
            } catch (Exception e) {
                // 一旦抛出异常，线程状态就会被设置为false
                System.out.println("线程被中断!");
                System.out.println("线程中断状态:" + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
        });
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
}
