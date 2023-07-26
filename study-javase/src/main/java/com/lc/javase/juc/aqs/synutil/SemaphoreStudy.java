package com.lc.javase.juc.aqs.synutil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreStudy {// 信号量
	// Semaphore（信号量） 是一个线程同步结构，用于在线程间传递信号（wait，notify，notifyAll），以避免出现信号丢失；
	// 或者像锁一样用于保护一个关键区域。

	// Semaphore当前在多线程环境下被扩放使用，操作系统的信号量是个很重要的概念，在进程控制方面都有应用。
	// Java 并发库 的Semaphore 可以很轻松完成信号量控制，Semaphore可以控制某个资源可被同时访问的个数，
	// 通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
	// 比如在Windows下可以设置共享文件的最大客户端访问个数。

	// Semaphore实现的功能就类似厕所有5个坑，假如有10个人要上厕所，那么同时只能有多少个人去上厕所呢？
	// 同时只能有5个人能够占用，当5个人中 的任何一个人让开后，其中等待的另外5个人中又有一个人可以占用了。
	// 另外等待的5个人中可以是随机获得优先机会，也可以是按照先来后到的顺序获得机会，这取决于构造Semaphore对象时传入的参数选项。
	// 单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
	public static void main(String[] args) {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 只能10个线程同时访问
		final Semaphore semp = new Semaphore(10);//把10换成1，可以当锁
		// 模拟20个客户端访问
		for (int index = 0; index < 20; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						semp.acquireUninterruptibly();
						semp.tryAcquire(11L, TimeUnit.SECONDS);
						//semp.tryAcquire(3000, TimeUnit.MILLISECONDS);
						System.out.println(NO + ":Accessed!");
						Thread.sleep((long) (Math.random() * 10000));
						// 访问完后，释放
						semp.release();
						System.out.println(NO + ":Released!——AvailablePermits：" + semp.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		// 退出线程池
		exec.shutdown();
	}
}
