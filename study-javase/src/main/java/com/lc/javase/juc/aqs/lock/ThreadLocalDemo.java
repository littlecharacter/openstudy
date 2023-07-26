package com.lc.javase.juc.aqs.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue() {
			return 0;
		}		
	};
	
	public static void main(String[] args) {
		ExecutorService ds = Executors.newCachedThreadPool();
		//VariableHolder2 vh = new VariableHolder2();
		Accessor3 a = new Accessor3(value);
		for (int i = 0; i < 5; i++) {			
			ds.execute(a);
		}
		ds.shutdown();
	}
}

class VariableHolder{
	//�ж��ٸ��߳̾ͻ�clone���ٷݣ��ֱ�ÿ���̲߳�����
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue() {
			return 0;
		}		
	};
	
	public static int getValue() {
		return value.get();
	}
	
	public static void increase() {
		value.set(value.get() + 1);
	}
}

class Accessor implements Runnable{
	@Override
	public void run() {
		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			VariableHolder.increase();	
			System.out.println(Thread.currentThread() + ":" + VariableHolder.getValue());
			Thread.yield();
		}	
	}
}

class VariableHolder2{
	//�ж��ٸ��߳̾ͻ�clone���ٷݣ��ֱ�ÿ���̲߳�����
	private ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue() {
			return 0;
		}		
	};
	
	public int getValue() {
		return value.get();
	}
	
	public void increase() {
		value.set(value.get() + 1);
	}
}

class Accessor2 implements Runnable{
	private VariableHolder2 vh;
	
	public Accessor2(VariableHolder2 vh) {
		this.vh = vh;
	}

	@Override
	public void run() {
		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			vh.increase();	
			System.out.println(Thread.currentThread() + ":" + vh.getValue());
			Thread.yield();
		}	
	}
}

class Accessor3 implements Runnable {
	private ThreadLocal<Integer> value;
	
	public Accessor3(ThreadLocal<Integer> value) {
		this.value = value;
	}

	@Override
	public void run() {
		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			value.set(value.get() + 1);	
			System.out.println(Thread.currentThread() + ":" + value.get());
			Thread.yield();
		}	
	}
	
}

