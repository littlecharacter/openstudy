package com.lc.javase.juc.queue;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueStudy {
    //没有容量的队列
    //仅当有足够多的消费者，并且总是有一个消费者在准备获取工作任务时，才适合同步队列
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        synchronousQueue.offer(1);
        synchronousQueue.poll();
        synchronousQueue.put(1);
        synchronousQueue.take();
    }
}
