package com.lc.javase.juc.aqs.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SlockDemo {
	//�������еĹ�����Դ --> �ٵģ�����ҲҪclone�ڶ��й���
	//�������еĹ�����Դ --> ����-����ʽ
	private static int count = 0;
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		OutThread r = new OutThread(count);
//		Runnable r = new Runnable() {
//			@Override
//			public void run() {	
//				while (true) {	
//					try {
//						TimeUnit.MILLISECONDS.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					synchronized (SlockDemo.class) {					
//						count ++;
//						System.out.println(Thread.currentThread() + ":" + count);
//					}
//				}
//			}
//		};
		for (int i = 0; i < 5; i++) {
			es.execute(r);
		}
		es.shutdown();
		
		//���´��룬֤���������е�count���ǹ�����Դ
		try {
			TimeUnit.MILLISECONDS.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(count);
	}
}

/**
 * ���еĹ�����Դ
 * @author LittleRW
 */
class OutThread implements Runnable{
	private int count;
	
	public OutThread(int count) {
		super();
		this.count = count;
	}

	@Override
	public void run() {	
		while (true) {	
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (this) {					
				count ++;
				System.out.println(Thread.currentThread() + ":" + count);
			}
		}
	}
}
