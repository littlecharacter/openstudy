package com.lc.javase.juc;

import com.lc.javase.juc.unsafe.Overswin;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class OverswinTest {
    @Test
    public void testOverswin() throws InterruptedException {
        while (true) {
            System.out.println("testOverswin:主线程开始执行!");
            final Overswin[] unsafe = new Overswin[1];

            new Thread(() -> {
                System.out.println("testOverswin:线程-1开始执行!");
                unsafe[0] = new Overswin(1);
            }).start();
            TimeUnit.MILLISECONDS.sleep(50L);
            new Thread(() -> {
                System.out.println("testOverswin:线程-2开始执行!");
                int property;
                try {
                    property = unsafe[0].getProperty();
                } catch (Exception e) {
                    //进入异常就说明java8在构造方法执行完之前，别的线程是拿不到this引用的
                    System.out.println("testOverswin:线程-2异常!!!!!!!!!!!!!!");
                    property = 0;
                }
                if (property == 1) {
                    System.exit(0);
                }
            }).start();
            TimeUnit.MILLISECONDS.sleep(1000L);
            System.out.println("testOverswin:主线程结束执行!");
            System.out.println("------------------------------------");
        }
    }
}