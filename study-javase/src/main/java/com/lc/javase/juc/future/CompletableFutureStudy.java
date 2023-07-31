package com.lc.javase.juc.future;

import java.util.concurrent.*;

/**
 * @author gujixian
 * @since 2023/7/22
 */
public class CompletableFutureStudy {
    public static void main(String[] args) throws Exception {
        simpleTest();
        normalTest();
    }

    private static void simpleTest() throws Exception {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "：计算结果中...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            future.complete("HelloWorld");
        }).start();
        System.out.println(Thread.currentThread().getName() + "：等待获取结果...");
        System.out.println(Thread.currentThread().getName() + "：获取结果 = " + future.get());
    }

    private static void normalTest() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        });
        future.thenApplyAsync((i) -> {
            return String.valueOf(i);
        });
        future.thenApplyAsync((i) -> {
            return String.valueOf(i);
        });
        future.thenRunAsync(() -> {
        }, Executors.newSingleThreadExecutor());

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {

        });
    }

}
