package com.lc.javase.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的用途
 * @author LittleRW
 */
public class ThreadPoolExecutorStudy {
	// 4中内置线程池
	// private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	// private static ExecutorService cachedThreadPool = Executors.newSingleThreadExecutor();
	// private static ScheduledExecutorService cachedThreadPool = Executors.newScheduledThreadPool(5);
	// private static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);

	public static void main(String[] args) {
		for (int i = 1; i <= 100; i++) {
			try {
				ThreadPoolUtil.getXxThreadPool().execute(new ThreadPoolTask(new String("任务" + i)));
				ThreadPoolUtil.getXxThreadPool().submit(new ThreadPoolTask(new String("任务" + i)));
				System.out.println("第" + i + "个线程加入线程池！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println(ThreadPoolUtil.getXxThreadPool());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class ThreadPoolTask implements Runnable {
		private String taskName;

		ThreadPoolTask(String taskName) {
			this.taskName = taskName;
		}

		@Override
        public void run() {
			try {
				System.out.println("start .." + taskName);
				TimeUnit.MILLISECONDS.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

