package com.lc.javase.juc.base;

public class VolatileStudy {
    int a = 0;
    volatile boolean flag = false;

    public void writer() {
        a = 1;
        flag = true;
    }

    public void reader() {
        if (flag) {
            System.out.println(a + 1);
        }
    }
}
