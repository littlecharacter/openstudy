package com.lc.javase.juc.atomic;

import com.lc.javase.other.pojo.User;

import java.util.concurrent.atomic.*;

public class AtomicStudy {
    private AtomicInteger ai = new AtomicInteger(1);
    private AtomicIntegerArray aiArray = new AtomicIntegerArray(16);

    private AtomicReference<String> arString = new AtomicReference<>();
    private AtomicReferenceArray<String> arStringArray = new AtomicReferenceArray<>(16);
    private AtomicReferenceFieldUpdater arfu = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");

    private AtomicStampedReference<Integer> asrInt = new AtomicStampedReference<>(1, 1);
}
