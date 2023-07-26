package com.lc.javase.juc.bq;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DelayQueueStudy {
	public void work() {
		ExecutorService es = Executors.newCachedThreadPool();
		DelayQueue<DelayTask> dq = new DelayQueue<DelayTask>();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			dq.put(new DelayTask("Task" + (i + 1), System.nanoTime() + random.nextInt(3) * (System.nanoTime() / 10000)));
		}
		es.execute(new TaskConsumer(dq));
		es.shutdown();
	}

	private class DelayTask implements Runnable, Delayed{
		private String name;
		private long delay;

		public DelayTask(String name, long delay) {
			super();
			this.name = name;
			this.delay = delay;
		}

		@Override
		public int compareTo(Delayed dd) {
			DelayTask that = (DelayTask)dd;
			if (this.delay < that.delay) {
				return -1;
			}
			if (this.delay > that.delay) {
				return 1;
			}
			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
            //System.nanoTime()比System.currentTimeMillis()更精确
            //System.out.println(delay);
			return unit.convert(delay - System.nanoTime(), TimeUnit.NANOSECONDS);
		}

		@Override
		public void run() {
			System.out.println(name + " run!");
		}
	}

	private class TaskConsumer implements Runnable{
		private DelayQueue<DelayTask> dq;

		public TaskConsumer(DelayQueue<DelayTask> dq) {
			super();
			this.dq = dq;
		}

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
					dq.take().run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("TaskConsumer finished!");
		}
	}
}