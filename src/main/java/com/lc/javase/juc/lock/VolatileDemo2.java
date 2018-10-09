package com.lc.javase.juc.lock;

import java.util.concurrent.CountDownLatch;

/**
 * volatile�ؼ������ã�
 * 1.��֤�ɼ��ԣ���һ���̸߳ı�countֵ�������б�volatile���εı������������߳̿��Կ�����
 * 2.��ֹ����ָ�����š�
 * @author LittleRW
 */
public class VolatileDemo2 {
	static Object o = null;
	static volatile Boolean isInit = false; //���VolatileDemoTask1�еĿ�������
	static CountDownLatch latch = new CountDownLatch(2);
	
	public static void main(String[] args) {
		new Thread(new VolatileDemoTask1()).start();
		latch.countDown();
		new Thread(new VolatileDemoTask2()).start();
		latch.countDown();
	}
	
	static class VolatileDemoTask1 implements Runnable{	
		@Override
		public void run() {
			try {
				latch.await();
				//��������������ţ��Ӷ�����VolatileDemoTask2����
				o = new Object();
				isInit = true;
				System.out.println(o.toString() + " " + isInit.toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class VolatileDemoTask2 implements Runnable{	
		@Override
		public void run() {
			try {
				latch.await();
				while (isInit) {
					System.out.println(o.toString() + " " + isInit.toString());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
