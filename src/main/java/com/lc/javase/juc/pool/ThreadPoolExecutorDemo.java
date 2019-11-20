package com.lc.concurrency.thread;

import com.lc.javase.juc.ThreadPoolExecutorUtil;

import java.util.concurrent.TimeUnit;

/**
 * 线程池的用途
 * @author LittleRW
 */
public class ThreadPoolExecutorDemo {
	public static void main(String[] args) {
		for (int i = 1; i <= 100; i++) {
			try {
				ThreadPoolExecutorUtil.getThreadPool().execute(new ThreadPoolTask(new String("任务" + i)));
				System.out.println("第" + i + "个线程加入线程池！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println(ThreadPoolExecutorUtil.getThreadPool());
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

