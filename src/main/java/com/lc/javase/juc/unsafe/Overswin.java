package com.lc.javase.juc.unsafe;

import java.util.concurrent.TimeUnit;

/**
 * 对应《Java 并发编程实战》程序清单3-7
 * 1、测试失败；
 * 2、可能是自己理解不够，也可能是java8已经优化；
 * 3、平时写的时候不这样写就是。
 */
public class Overswin {
    private int property;

    public Overswin(int property) {
        System.out.println("Overswin:构造方法开始执行!");
        this.setProperty(0);
        try {
            TimeUnit.MILLISECONDS.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Overswin:执行睡眠后的操作!");
        this.property = property;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }
}
