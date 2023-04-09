package com.lc.javase.juc.synutil;

import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrierStudy {
	private static final int THREAD_NUM = 5;

	private ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_NUM);

	private int barrier = 0;

	public void work() {

        CyclicBarrier cb = new CyclicBarrier(THREAD_NUM, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第" + (barrier++) + "轮任务完成！");
        });

        for (int j = 0; j < THREAD_NUM; j++) {
            threadPool.submit(new WorkerThread(cb, "任务" + j));
        }

        threadPool.shutdown();
	}

    private class WorkerThread implements Runnable {

        private CyclicBarrier barrier;
        private String name;

        public WorkerThread(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            // 这里可以控制工作几轮结束
            while (true) {
                try {
                    System.out.println(name + " is working...");
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(3) * 1000 + 1000);
                    // 此线程工作完成，在这里等待，直到所有线程都到达barrier。
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
