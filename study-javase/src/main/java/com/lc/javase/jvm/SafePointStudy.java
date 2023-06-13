package com.lc.javase.jvm;

import java.util.concurrent.TimeUnit;

/**
 * 安全点学习：https://zhuanlan.zhihu.com/p/286110609
 */
public class SafePointStudy {
    private static final Thread t1 = new Thread(() -> {
        while (true) {
            for (int i = 1; i <= 1000000000; i++) {
                boolean b = 1.0 / i == 0;
            }
        }
    });

    // 不触发 GC 也可能进入安全点
    // private static final Thread t2 = new Thread(() -> {
    //     while (true) {
    //         try {
    //             TimeUnit.MILLISECONDS.sleep(5L);
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //         byte[] bytes = new byte[50 * 1024];
    //     }
    // });

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        // t2.start();
        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            TimeUnit.MILLISECONDS.sleep(1000L);
            long cost = System.currentTimeMillis() - start;
            (cost > 1100L ? System.err : System.out).printf("thread: %s, costs %d ms\n", Thread.currentThread().getName(), cost);
        }
    }
}
