package com.lc.javase.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
	public static void main(String[] args) {
		ExecutorService es1 = Executors.newCachedThreadPool();
		ExecutorService es2 = Executors.newFixedThreadPool(5);
		ExecutorService es3 = Executors.newSingleThreadExecutor();
		ExecutorService es4 = Executors.newScheduledThreadPool(5);
		for (int i = 0; i < 5; i++) {
			es1.execute(new LiftOff(5));
		}
//		es1.shutdown();
//		es1.shutdownNow();
	}

	private static class LiftOff implements Runnable{
		private int countDown;

		public LiftOff(int countDown) {
			super();
			this.countDown = countDown;
		}

		@Override
		public void run() {
			while (countDown-- > 0) {
				System.out.println(Thread.currentThread() + ":" + countDown);
				Thread.yield();
			}
		}
	}
}
