package com.lc.javase.juc;

import java.util.concurrent.TimeUnit;

/**
 * �̳߳ص���;
 * @author LittleRW
 */
public class ThreadPoolExecutorDemo {
	public static void main(String[] args) {
		for (int i = 1; i <= 100; i++) {
			try {
				ThreadPoolExecutorUtil.getThreadPool().execute(new ThreadPoolTask(new String("����" + i)));
				System.out.println("��" + i + "���̼߳����̳߳أ�");
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
}

class ThreadPoolTask implements Runnable {
	private String taskName;

	ThreadPoolTask(String taskName) {
		this.taskName = taskName;
	}

	public void run() {
		try {
			System.out.println("start .." + taskName);
			TimeUnit.MILLISECONDS.sleep(30000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
