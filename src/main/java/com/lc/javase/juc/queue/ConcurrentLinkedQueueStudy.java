package com.lc.javase.juc.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueStudy {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        concurrentLinkedQueue.offer(11);
        Integer result = concurrentLinkedQueue.poll();
        System.out.println(result);
        result = concurrentLinkedQueue.poll();
        System.out.println(result);
    }
}
