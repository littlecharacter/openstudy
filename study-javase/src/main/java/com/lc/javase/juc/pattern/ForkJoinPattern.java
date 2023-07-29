package com.lc.javase.juc.pattern;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author gujixian
 * @since 2023/7/28
 */
public class ForkJoinPattern {
    private static final Random RANDOM = new Random();
    public static void main(String[] args) throws Exception {
        int N = 15;
        CyclicBarrier cb = new CyclicBarrier(2, () -> {
            System.out.println("-----------------------------------");
        });
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("singleThread 计算中..");
                    long start = System.currentTimeMillis();
                    int result = singleThread(N);
                    System.out.println("singleThread(" + N + ")=" + result + "，耗时：" + (System.currentTimeMillis() - start) + "ms");
                    cb.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("multiThread 计算中..");
                    long start = System.currentTimeMillis();
                    int result = multiThread(N);
                    System.out.println("multiThread(" + N + ")=" + result + "，耗时：" + (System.currentTimeMillis() - start) + "ms");
                    cb.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private static int singleThread(int n) {
        consumeTime();
        if (n <= 1) {
            return 1;
        }
        int r1 = singleThread(n-1);
        int r2 = singleThread(n-2);
        return r1 + r2;
    }

    private static int multiThread(int n) {
        // 为了追踪子线程名称，需要重写 ForkJoinWorkerThreadFactory 的方法
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("my-thread-" + worker.getPoolIndex());
            return worker;
        };
        ForkJoinPool forkJoinPool = new ForkJoinPool(5, factory, null, false);
        // 创建递归任务
        Fibonacci fibonacci = new Fibonacci(n);
        // 分治计算任务
        Integer result = forkJoinPool.invoke(fibonacci);
        // System.out.println("Fibonacci(" + n + ")的结果为：" + result);
        return result;
    }


    private static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        public Integer compute() {
            consumeTime();
            // base case
            if (n <= 1) {
                // System.out.println(Thread.currentThread().getName()+"计算：Fibonacci（"+ n + ")=" + 1);
                return 1;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork(); // 拆分
            Fibonacci f2 = new Fibonacci(n - 2);
            f2.fork(); // 拆分
            // 合并
            int result = f2.join() + f1.join();
            // System.out.println(Thread.currentThread().getName()+"计算：Fibonacci（"+ n + ")=" + result);
            return result;
        }
    }

    private static void consumeTime() {
        // 模拟计算耗时
        int x = 1;
        for (int i = 0; i < 5000; i++) {
            x = x * Math.min(16, RANDOM.nextInt(18)) / Math.max(15, RANDOM.nextInt(17));
        }
    }
}
