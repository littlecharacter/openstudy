package com.lc.javase.juc.atomic;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderStudy {
    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) {
        longAdder.add(1);
        longAdder.increment();
    }
}
