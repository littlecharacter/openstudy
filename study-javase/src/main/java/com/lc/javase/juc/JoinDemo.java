package com.lc.javase.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JoinDemo {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new SleepThread(10));
		es.shutdown();
	}
}

class JoinThread implements Runnable{
	private int countDown;

	public JoinThread(int countDown) {
		super();
		this.countDown = countDown;
	}

	@Override
	public void run() {
		while (countDown-- > 0) {
			System.out.println(Thread.currentThread() + ":" + countDown);		
		}
	}
}

class SleepThread implements Runnable{
	private int countDown;
	
	public SleepThread(int countDown) {
		super();
		this.countDown = countDown;
	}

	@Override
	public void run() {
		while (countDown-- > 0) {
			if (countDown == 5) {
				try {
					Thread t = new Thread(new JoinThread(10));
					t.start();
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread() + ":" + countDown);		
		}
	}
}