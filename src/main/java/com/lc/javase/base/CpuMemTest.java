package com.lc.javase.base;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CpuMemTest {
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int spinTime = Integer.valueOf(args[0]);
        for (;;) {
            // ThreadPoolFactory.getXxThreadPool().submit(new Permutation());
            threadPool.submit(new Permutation());
            TimeUnit.SECONDS.sleep(spinTime);
        }
    }




    static class Permutation implements Runnable {
        int length = 200000;
        int[] nums = new int[length];

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = 0; i < length; i++) {
                nums[i] = new Random().nextInt(length);
            }
            insertionSort(nums);
            System.out.println(Thread.currentThread().getName() + "耗时：" + (System.currentTimeMillis() - start) / 1000 + "s");
        }

        private void insertionSort(int[] array) {
            for (int i = 1; i < array.length; i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = key;
            }
        }
    }
}
