package com.lc.javase;

import com.lc.javase.base.StringStudy;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("go...");
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                    System.out.println("num = " + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Runnable runnable = () -> {
            while (true) {
                for (int i = 0; i < 500000000; i++) {
                    num.getAndAdd(1);
                }
                System.out.println(Thread.currentThread().getName() + "执行结束!");
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
    }
}
