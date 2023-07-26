package com.lc.javase.juc.aqs.synutil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CountDownLatchStudy {
	public void work() {
		ExecutorService es = Executors.newCachedThreadPool();
		List<Future<Integer>> resultList = new ArrayList<>();
		CountDownLatch latch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
			resultList.add(es.submit(new TaskPortion(latch, (i * 10 + 1), (i + 1) * 10)));
		}
		es.execute(new Result(latch, resultList));
		es.shutdown();
	}

	private class TaskPortion implements Callable<Integer>{
		private CountDownLatch latch;
		private int begin;
		private int end;

		public TaskPortion(CountDownLatch latch, int begin, int end) {
			super();
			this.latch = latch;
			this.begin = begin;
			this.end = end;
		}

		@Override
		public Integer call() throws Exception {
			int result = 0;
			try {
				TimeUnit.MILLISECONDS.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = begin; i <= end; i++) {
				result += i;
			}
			System.out.println(Thread.currentThread() + ":" + "计算完毕！");
			latch.countDown();
			return result;
		}
	}

    private class Result implements Runnable{
		private CountDownLatch latch;
		List<Future<Integer>> resultList;

		public Result(CountDownLatch latch, List<Future<Integer>> resultList) {
			super();
			this.latch = latch;
			this.resultList = resultList;
		}

		@Override
		public void run() {
			int result = 0;
			try {
				latch.await();
				latch.await(111L, TimeUnit.DAYS);
				for (Future<Integer> future : resultList) {
					result += future.get();
				}
				System.out.println("计算结果：" + result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}










