package com.lc.javase.thread.synutil;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerStudy {
    private static final int SIZE = 2;
	//Exchanger可以在两个线程之间交换数据，只能是2个线程，他不支持更多的线程之间互换数据。
	//当线程A调用Exchange对象的exchange()方法后，他会陷入阻塞状态，直到线程B也调用了exchange()方法，以线程安全的方式交换数据，
	//之后线程A和B继续运行
	public void exchange() {
		Exchanger<List<Integer>> exchanger = new Exchanger<>();
		new AThread(exchanger).start();
		new BThread(exchanger).start();
	}

	private class AThread extends Thread {
		List<Integer> listA = new ArrayList<>();
		Exchanger<List<Integer>> exchanger = null;

		public AThread(Exchanger<List<Integer>> exchanger) {
			super();
			this.exchanger = exchanger;
		}

		@Override
		public void run() {
			Random rand = new Random();
			while (true) {
				listA.clear();
				for (int i = 0; i < SIZE; i++) {
					listA.add(rand.nextInt(10000));
				}
                System.out.println("A产生的数据集 : " + JSON.toJSONString(listA));
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
					listA = exchanger.exchange(listA);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                System.out.println("A交换的数据集 : " + JSON.toJSONString(listA));
			}
		}
	}

	private class BThread extends Thread {
		List<Integer> listB = new ArrayList<>();
		Exchanger<List<Integer>> exchanger = null;

		public BThread(Exchanger<List<Integer>> exchanger) {
			super();
			this.exchanger = exchanger;
		}

		@Override
		public void run() {
			Random rand = new Random();
			while(true){
				listB.clear();
				for (int i = 0; i < SIZE; i++) {
					listB.add(rand.nextInt(10000));
				}
                System.out.println("B产生的数据集 : " + JSON.toJSONString(listB));
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
					listB = exchanger.exchange(listB);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                System.out.println("B交换的数据集 : " + JSON.toJSONString(listB));
			}
		}
	}
}