package com.lc.javase.juc.aqs.synutil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量：控制某个资源可被同时访问的个数
 * 1. 通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 * 2. 信号量可以实现公平地获取许可
 *
 * 举个例子：
 * Semaphore实现的功能就类似厕所有5个坑，假如有10个人要上厕所，那么同时只能有多少个人去上厕所呢？
 * 同时只能有5个人能够占用，当5个人中 的任何一个人让开后，其中等待的另外5个人中又有一个人可以占用了。
 * 另外等待的5个人中可以是随机获得优先机会，也可以是按照先来后到的顺序获得机会，这取决于构造 Semaphore 对象时传入的参数选项。
 *
 * 再一个例子：
 * 单个信号量的 Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合
 *
 * @author gujixian
 * @since 2023/8/1
 */
public class SemaphoreStudy {
	public static void main(String[] args) {
		// 线程池
		ExecutorService es = Executors.newCachedThreadPool();
		// 只能10个线程同时访问
		final Semaphore semaphore = new Semaphore(3);//把10换成1，可以当锁
		// 模拟20个客户端访问
		for (int index = 0; index < 10; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semaphore.acquire();
						// semaphore.acquireUninterruptibly();
						// semaphore.tryAcquire(11L, TimeUnit.SECONDS);
						// semaphore.tryAcquire(3000, TimeUnit.MILLISECONDS);
						System.out.println(NO + ":Accessed!");
						Thread.sleep((long) (Math.random() * 10000));
						// 访问完后，释放
						semaphore.release();
						System.out.println(NO + ":Released!——AvailablePermits：" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			es.execute(run);
		}
		// 退出线程池
		es.shutdown();
	}
}
