package com.lc.javase.thread.synutil;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierLearn {
	private static final int THREAD_NUM = 5;

	public void work() {
        // 当所有线程到达barrier时执行
        CyclicBarrier cb = new CyclicBarrier(THREAD_NUM, () -> {
            try {
                //当在cb上等待的线程为THREAD_NUM时，此段代码执行
                System.out.println("任务 is in barrier waiting...");
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

		for (int i = 0; i < THREAD_NUM; i++) {
			new Thread(new WorkerThread(cb, "任务" + i)).start();
		}
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
            while (true) {
                try {
                    //System.out.println(name + " is waiting...");
                    // 线程在这里等待，直到所有线程都到达barrier。
                    barrier.await();
                    System.out.println(name + " is working...");
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(3) * 1000 + 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
