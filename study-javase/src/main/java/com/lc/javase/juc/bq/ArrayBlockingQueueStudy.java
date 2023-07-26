package com.lc.javase.juc.bq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueStudy {
    //类似ArrayList，但又比其又更好的迸发性能
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(16);
        queue.offer(1);
        queue.peek();
        queue.poll();
        queue.poll(3, TimeUnit.MILLISECONDS);
        queue.take();
    }
}
