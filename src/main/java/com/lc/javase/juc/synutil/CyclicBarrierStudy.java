package com.lc.javase.juc.synutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierStudy {
	private static final int HORSE_NUM = 5;

	public void race() {
        new HorseRace(HORSE_NUM);
    }

    private class HorseRace{
        static final int FINISH_LINE = 50;
        private List<Horse> horseList = new ArrayList<Horse>();
        private ExecutorService es = Executors.newCachedThreadPool();
        private CyclicBarrier barrier;

        public HorseRace(int nHorse) {
            barrier = new CyclicBarrier(nHorse, new Runnable() {
                @Override
                public void run() {//CyclicBarrier使其自动循环
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i <= FINISH_LINE; i++) {
                        sb.append("-");
                    }
                    System.out.println("跑道:" + sb.toString());

                    for (Horse horse : horseList) {
                        horse.tracks();
                    }

                    for (Horse horse : horseList) {
                        if (horse.getStrides() >= FINISH_LINE) {
                            System.out.println("跑道:" + sb.toString());
                            System.out.println(horse.getName() + " won!");
                            es.shutdownNow();
                            return;
                        }
                    }
                }
            });

            for (int i = 0; i < nHorse; i++) {
                Horse horse = new Horse("赛马" + i, barrier);
                horseList.add(horse);
                es.execute(horse);
            }
        }
    }

    private class Horse implements Runnable{
        private String name;
        private int strides = 0;
        private Random random = new Random();
        private CyclicBarrier barrier;

        public Horse(String name, CyclicBarrier barrier) {
            super();
            this.name = name;
            this.barrier = barrier;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public int getStrides() {
            return strides;
        }
        public void setStrides(int strides) {
            this.strides = strides;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    barrier.await();
                    strides += random.nextInt(3);
                    TimeUnit.MILLISECONDS.sleep(150);
                } catch (Exception e) {
                    break;
                }
            }
        }

        public void tracks(){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < strides && i < HorseRace.FINISH_LINE; i++) {
                sb.append("*");
            }
            System.out.println(name + ":" + sb.toString());
        }
    }
}








