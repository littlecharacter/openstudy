package com.lc.pattern.builder.singleton;

import java.util.concurrent.CountDownLatch;

public class SingletonDemo2 {
	private static final int SIZE = 2000;
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(SIZE);
		for (int i = 0; i < SIZE; i++) {
			new Thread(new SingletonTask(latch)).start();
			latch.countDown();
		}
	}

	private static class SingletonTask implements Runnable{
		CountDownLatch latch;

		public SingletonTask(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				latch.await();
				ToolkitUp singleton = ToolkitUp.getDefaultToolkit();
				System.out.println(singleton);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

