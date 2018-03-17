package com.lc.javase.thread.synutil;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * FutureTask可用于异步获取执行结果或取消执行任务的场景。
 * @author LittleRW
 */
public class FutureTaskLearn {
	public static void main(String[] args) throws Exception {
		FutureTask<String> result = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				TimeUnit.MILLISECONDS.sleep(10000);
				return "hello!";
			}
		});
		result.run();

		System.out.println(result.get());
	}
}