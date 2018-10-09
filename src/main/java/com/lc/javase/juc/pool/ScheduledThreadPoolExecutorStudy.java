package com.lc.javase.juc.pool;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorStudy {//见名知意
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor se = new ScheduledThreadPoolExecutor(10);
		se.scheduleAtFixedRate(() -> System.out.println("run..."), 1, 2, TimeUnit.SECONDS);
        se.scheduleWithFixedDelay(() -> System.out.println("run..."),1, 3, TimeUnit.SECONDS);
		//se.schedule(null, 0, null);
	}
}
