package com.lc.javase.juc.aqs.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 哲学家吃饭 - 死锁
 */
public class PhilosopherDemo {
	private static final int SIZE = 5;
	public static void main(String[] args) {
		Chopstick[] chopsticks = new Chopstick[SIZE];
		for (int i = 0; i < SIZE; i++) {
			chopsticks[i] = new Chopstick();
		}
 		ExecutorService es = Executors.newCachedThreadPool();
 		for (int i = 0; i < SIZE; i++) {
 			Thread t = new Thread(new Philosopher(chopsticks[i], chopsticks[i % SIZE]));
// 			if (i % 2 != 0) {
// 				t.setPriority(Thread.MAX_PRIORITY);
//			}
 			es.execute(t);
 		}
		es.shutdown();
	}

	private static class Philosopher implements Runnable{
		private Chopstick left;
		private Chopstick right;

		public Philosopher(Chopstick left, Chopstick right) {
			this.left = left;
			this.right = right;
		}

		private void eat() {
			try {
				System.out.println(Thread.currentThread() + ":" + "eating...");
				TimeUnit.MILLISECONDS.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (true) {
				left.take();
				System.err.println(Thread.currentThread() + ":" + "taking left chopstick...");
				right.take();
				System.err.println(Thread.currentThread() + ":" + "taking right chopstick...");
				eat();
				left.drop();
				right.drop();
			}
		}
	}

	private static class Chopstick{
		private boolean taken = false;

		public synchronized void take() {
			while (taken) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			taken = true;
		}

		public synchronized void drop() {
			taken = false;
			notifyAll();
		}
	}
}