package com.lc.javase.juc.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
	public static void main(String[] args) {
		ExecutorService ds = Executors.newCachedThreadPool();
		IncrementerThread it = new IncrementerThread();
		for (int i = 0; i < 5; i++) {			
			ds.execute(it);
		}
		ds.shutdown();
		int value = 0;
		while(value < 10000){
			value = it.getValue();
			System.out.println(value);
			if (value % 2 != 0 || value > 10000) {
				System.exit(0);
			}
		}
	}
}

/**
 * ���еĹ�����Դ
 * @author LittleRW
 */
class IncrementerThread implements Runnable{
	private int i = 0;
	
	private Lock lock = new ReentrantLock();
	public int getValue() {
		lock.lock();
		try {
			return i;//����return i��Ȼ��ԭ���Բ��������ǻ���Ҫ������������������п���i++һ�ε�ʱ�򷵻ء�
		} finally {
			lock.unlock();
		}
	}
	
	public void evenIncrease(){
		lock.lock();
		try {
			i++;
			i++;
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public void run() {
		while (true) {
			evenIncrease();
		}	
	}
}
