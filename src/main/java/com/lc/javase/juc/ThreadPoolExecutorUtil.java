package com.lc.javase.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorUtil {
	
	private static class ThreadPoolExecutorUtilInner{
		//private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		//private static ExecutorService cachedThreadPool = Executors.newSingleThreadExecutor();
		//private static ScheduledExecutorService cachedThreadPool = Executors.newScheduledThreadPool(5);
		private static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);
	}
	
	public static ExecutorService getThreadPool() {
		ExecutorService es = ThreadPoolExecutorUtilInner.cachedThreadPool;
		return es;
	}
}
