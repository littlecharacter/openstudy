package com.lc.javase.juc;

import java.util.concurrent.TimeUnit;

public class ThreadDemo {
	public static void main(String[] args) {
		Thread t = new Thread(new TestThread());
		System.out.println(t.getState());
		t.start();
		System.out.println(t.getState());
		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(t.getState());
		}
	}
}

class TestThread implements Runnable{
	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
