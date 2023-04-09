package com.lc.javase.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 有人测试可以复现，cpu核数越多复现的几率越大
 * jdk7：扩容时都可能导致死锁
 * jdk8(java version "1.8.0_111")：在PutTreeValue时可能死循环,死循环在hashMap的1816行或2229行
 * jstack发现可能卡在 at java.util.HashMap$TreeNode.balanceInsertion(HashMap.java:2229)
 * 也有可能卡在 at java.util.HashMap$TreeNode.root(HashMap.java:1816)
 */
public class HashMapBug {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new HashMapThread().start();
        }
    }

    static class HashMapThread extends Thread {
        private AtomicInteger ai = new AtomicInteger(0);
        private static Map<Integer, Integer> map = new HashMap<Integer, Integer>(1);

        @Override
        public void run() {
            while (ai.get() < 100000) {
                map.put(ai.get(), ai.get());
                System.out.println(Thread.currentThread().getName() + "：插入成功");
                ai.incrementAndGet();
            }
            System.out.println(Thread.currentThread().getName() + "：执行结束完");
        }
    }
}
