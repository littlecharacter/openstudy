package com.lc.javase.juc.aqs.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * volatile�ؼ������ã�
 * 1.��֤�ɼ��ԣ���һ���̸߳ı�countֵ�������б�volatile���εı������������߳̿��Կ�����
 * 2.��ֹ����ָ�����š�
 * @author LittleRW
 */
public class VolatileDemo {//����һ����������ô�ƣ�volatile+ԭ�Ӳ���=��
	public static void main(String[] args) {
		Counter c = new Counter();
		ExecutorService ds = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {			
			ds.execute(new CounterThread(c));
		}
		ds.shutdown();
		while(Thread.activeCount() > 1)  //��֤ǰ����̶߳�ִ����
            Thread.yield();
		System.out.println(c.getCount());
	}
}

/**
 * ���еĹ�����Դ
 * @author LittleRW
 */
class Counter{
	private volatile int count = 0;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public void increase() {
		count ++;
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "Resourse [count=" + count + "]";
	}
}

class CounterThread implements Runnable{
	private Counter c;

	public CounterThread(Counter c) {
		super();
		this.c = c;
	}

	public void run(){
		for(int i = 0; i < 1000; i++){
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			c.increase();
		}
	}
}
