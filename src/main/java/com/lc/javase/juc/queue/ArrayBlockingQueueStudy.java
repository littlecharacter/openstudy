package com.lc.javase.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueStudy {
    //类似ArrayList，但又比其又更好的迸发性能
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(16);
        queue.offer(1);
        queue.peek();
        queue.poll();
    }
}
