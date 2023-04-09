package com.lc.javase.juc.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueStudy {
    //类似LinkedList，但又比其又更好的迸发性能
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(16);
        queue.offer(1);
        queue.peek();
        queue.poll();
        queue.poll(3, TimeUnit.MILLISECONDS);
        queue.take();
    }
}
