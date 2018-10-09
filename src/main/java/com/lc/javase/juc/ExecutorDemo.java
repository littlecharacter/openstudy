package com.lc.javase.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
	public static void main(String[] args) {
		//ExecutorService es = Executors.newCachedThreadPool();
		//ExecutorService es = Executors.newFixedThreadPool(5);
		ExecutorService es = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++) {
			es.execute(new LiftOff(5));
		}
		es.shutdown();
	}
}

class LiftOff implements Runnable{
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
