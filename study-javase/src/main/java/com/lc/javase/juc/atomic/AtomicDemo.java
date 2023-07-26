package com.lc.javase.juc.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
	public static void main(String[] args) {
		ExecutorService ds = Executors.newCachedThreadPool();
		AtomicThread at = new AtomicThread();
		for (int i = 0; i < 5; i++) {			
			ds.execute(at);
		}
		ds.shutdown();
		int value = 0;
		while(true){
			value = at.getValue();
			System.out.println(value);
			if (value % 2 != 0) {
				System.exit(0);
			}
		}
	}

	private static class AtomicThread implements Runnable{
		private volatile AtomicInteger ai = new AtomicInteger(0);

		public int getValue() {
			return ai.get();
		}

		public void evenIncrease(){
			ai.addAndGet(2);//��Чint��ai += 2;
			//ai.addAndGet(1);
			//ai.addAndGet(1);
		}

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(100);
					evenIncrease();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}