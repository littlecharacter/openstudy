package com.lc.javase.thread.synutil;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierSimple {
    public void work() {
        CyclicBarrier barrier = new CyclicBarrier(3);  // 3
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new Thread(new Runner(barrier, "zhangsan")));
        executor.submit(new Thread(new Runner(barrier, "lisi")));
        executor.submit(new Thread(new Runner(barrier, "wangwu")));

        executor.shutdown();
    }

    private class Runner implements Runnable {
        private CyclicBarrier barrier;
        private String name;

        public Runner(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(name + " 准备OK.");
                    barrier.await();
                    System.out.println(name + " Go!!");
                    Thread.sleep(1000 * (new Random()).nextInt(5));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
