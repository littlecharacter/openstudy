package com.lc.javase.juc.thread;

import java.util.concurrent.TimeUnit;

public class ThreadStatus {
	public static void main(String[] args) {
		Thread t = new Thread(new StatusThread());
		System.out.println("线程状态：" + t.getState());
		t.start();
		System.out.println("线程状态：" + t.getState());
		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("线程状态：" + t.getState());
		}
	}

	private static class StatusThread implements Runnable {
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

