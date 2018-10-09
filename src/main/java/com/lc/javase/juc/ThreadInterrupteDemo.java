package com.lc.javase.juc;

public class ThreadInterrupteDemo {
	public static void main(String[] args) {
		Thread t = new Thread(new ZuseThread());
		t.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t.interrupt();
		System.out.println(Thread.currentThread() + ":" + "over!");
	}
}

class ZuseThread implements Runnable{
	private boolean flag = true;
	@Override
	public synchronized void run() {
		while (flag) {
			try {
				wait();//wait������ͬ��������ͬ���������ʹ�ã���Ϊ����Ҫ����notify��notifyAllҲ��
			} catch (InterruptedException e) {
				System.out.println(Thread.interrupted());
				e.printStackTrace();
				flag = false;
			} finally {
				System.out.println(Thread.currentThread() + ":" + "stoped!");
			}
		}
		System.out.println(Thread.currentThread() + ":" + "stoped!");
	}
}
