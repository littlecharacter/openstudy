package com.lc.javase.thread.pattern;

/**
 * 工作密取模式：
 */
public class WorkStealingPattern {
    //1、每个消费者都有各自的双端队列；
    //2、如果一个消费者完成了自己双端队列中的全部工作，那么它可以从其它消费者双端队列末尾秘密地获取工作。
    //借助双端队列Deque
    //工作密取模式非常适用于既是消费者也是生产者场景
}
