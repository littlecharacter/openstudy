package com.lc.javase.juc.thread;

import java.util.concurrent.TimeUnit;

public class JoinDemo {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("MainThread 执行中...");
		Thread t = new Thread(new JoinThread());
		t.start();
		t.join();
		System.out.println("MainThread 恢复执行~~");
		System.out.println("MainThread 执行结束！");
	}

	private static class JoinThread implements Runnable{
		@Override
		public void run() {
			System.out.println("JoinThread 加塞执行...");
			for (int i = 0; i < 5; i++) {
				System.out.println("JoinThread:" + i);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			System.out.println("JoinThread 执行结束！");
		}
	}
}