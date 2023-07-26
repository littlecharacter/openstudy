package com.lc.javase.juc.aqs.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLockDemo {
	public static void main(String[] args) {
		Resource r = new Resource();
		ExecutorService ds = Executors.newCachedThreadPool();
		ds.execute(new DeadLockA(r));
		ds.execute(new DeadLockB(r));
		ds.shutdown();
	}
}

/**
 * ���еĹ�����Դ
 * @author LittleRW
 */
class Resource{
	private int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Resource [count=" + count + "]";
	}
}

class MyLock{
	static Object lockA = new Object();
	static Object lockB = new Object();
}

class DeadLockA implements Runnable{
	private Resource r;

	public DeadLockA(Resource r) {
		super();
		this.r = r;
	}

	public void run(){
		while(true){
			synchronized(MyLock.lockA){
				System.out.println(Thread.currentThread() + ":lockA is already got, lockB is getting...");
				synchronized(MyLock.lockB){
					Thread.yield();//������ܳ������⣬�˷����������ӿ�����
					System.out.println(Thread.currentThread() + ":lockA is already got, lockB is got!");
					r.setCount(r.getCount() + 1);
					System.out.println(Thread.currentThread() + ":" + r.getCount());
				}
			}
		}
	}
}

class DeadLockB implements Runnable{
	private Resource r;

	public DeadLockB(Resource r) {
		super();
		this.r = r;
	}

	public void run(){
		while(true){
			synchronized(MyLock.lockB){
				System.out.println(Thread.currentThread() + ":lockB is already got, lockA is getting...");
				synchronized(MyLock.lockA){
					Thread.yield();//������ܳ������⣬�˷����������ӿ�����
					System.out.println(Thread.currentThread() + ":lockB is already got, lockA is got!");
					r.setCount(r.getCount() + 1);
					System.out.println(Thread.currentThread() + ":" + r.getCount());
				}
			}
		}
	}
}
