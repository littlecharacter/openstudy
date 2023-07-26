package com.lc.javase.juc.thread.pool;

import com.lc.javase.juc.thread.pool.factory.ThreadPoolFactory;

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
				ThreadPoolFactory.getXxThreadPool().execute(new ThreadPoolTask(new String("任务" + i)));
				ThreadPoolFactory.getXxThreadPool().submit(new ThreadPoolTask(new String("任务" + i)));
				System.out.println("第" + i + "个线程加入线程池！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println(ThreadPoolFactory.getXxThreadPool());
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

