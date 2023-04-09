package com.lc.javase.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableDemo {
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		
		final List<Future<String>> resultList = new ArrayList<Future<String>>();
		for (int i = 0; i < 5; i++) {
			resultList.add(es.submit(new TaskWithResult(10)));
		}
		
		es.execute(new Runnable() {//������һ���̴߳��������
			@Override
			public void run() {
				for (Future<String> future : resultList) {
					try {			
						System.out.println(future.get());//get()��������
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		});	
		es.shutdown();
		
		System.out.println("���������Ĳ���...");
	}
}

class TaskWithResult implements Callable<String>{
	private int countDown;

	public TaskWithResult(int countDown) {
		super();
		this.countDown = countDown;
	}

	@Override
	public String call() throws Exception {
		int result = 0;
		
		while (countDown-- > 0) {
			result += countDown;	
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		
		return Thread.currentThread() + ":" + result;
	}	
}
