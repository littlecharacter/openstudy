package com.lc.javase.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorInterrupteDemo {
	public static void main(String[] args) {
		//interrupteType01();
		interrupteType02();
	}

	private static void interrupteType01() {
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new BlockedThread());
		try {
			TimeUnit.MILLISECONDS.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		es.shutdownNow();
		System.out.println(Thread.currentThread() + ":" + "stoped!");	
	}
	
	private static void interrupteType02() {
		ExecutorService es = Executors.newCachedThreadPool();
		Future<?> future = es.submit(new BlockedThread());
		System.out.println(future);
		try {
			TimeUnit.MILLISECONDS.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		future.cancel(true);
		es.shutdown();
		System.out.println(Thread.currentThread() + ":" + "stoped!");	
	}
}

class BlockedThread implements Runnable{
	private boolean flag = true;
	@Override
	public synchronized void run() {
		while (flag) {
			try {
				wait();//wait������ͬ��������ͬ���������ʹ�ã���Ϊ����Ҫ����notify��notifyAllҲ��
			} catch (InterruptedException e) {
				e.printStackTrace();
				flag = false;
			} finally {
				System.out.println(Thread.currentThread() + ":" + "stoped!");
			}
		}
		System.out.println(Thread.currentThread() + ":" + "stoped!");
	}
}