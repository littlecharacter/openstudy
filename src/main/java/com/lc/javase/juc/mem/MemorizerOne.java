package com.lc.javase.juc.mem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MemorizerOne {
    private final Map<String, Result> cache = new ConcurrentHashMap<>();

    public Result get(String key) {
        if (key == null) {
            return null;
        }
        // 如果用双重否定锁，请问指令重排怎么办
        Result result = cache.get(key);
        if (result == null) {
            synchronized (this) {
                if (result == null) {
                    result = this.buildResult();
                }
            }
        }
        return result;
    }

    private Result buildResult() {
        System.out.println("一个很耗时的构建过程...");
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Result();
    }

    static class Result {
        int field1;
        int field2;
    }
}
