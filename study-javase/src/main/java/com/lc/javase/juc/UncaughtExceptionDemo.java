package com.lc.javase.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class UncaughtExceptionDemo {
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool(new HandlerThreadFactory());
		Thread t = new Thread(new ExceptionThread(), "�쳣�߳�");
		es.execute(t);
	}
}

class ExceptionThread implements Runnable{
	@Override
	public void run() {
		System.out.println(Thread.currentThread());
		throw new RuntimeException();
	}		
}

class ExceptionHandler implements Thread.UncaughtExceptionHandler{
	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {		
		System.out.print(Thread.currentThread() + ":");
		throwable.printStackTrace();
	}	
}

class HandlerThreadFactory implements ThreadFactory{
	@Override
	public Thread newThread(Runnable runnable) {
		Thread t = new Thread(runnable);
		t.setUncaughtExceptionHandler(new ExceptionHandler());
		return t;
	}	
}




