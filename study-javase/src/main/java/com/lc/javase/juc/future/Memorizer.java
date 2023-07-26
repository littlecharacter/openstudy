package com.lc.javase.juc.future;

import java.util.Map;
import java.util.concurrent.*;

public class Memorizer {
    private final Map<String, Future<Result>> cache = new ConcurrentHashMap<>();

    public Result get(String key) {
        if (key == null) {
            return null;
        }

        Future<Result> future = cache.get(key);
        // 如果缓存不存在则去构建一个缓存
        if (future == null) {
            FutureTask<Result> futureTask = this.buildResult();
            future = cache.putIfAbsent(key, futureTask);
            if (future == null) {
                future = futureTask;
                futureTask.run();
            }
        }

        try {
            return future.get();
        } catch (CancellationException e) {
            e.printStackTrace();
            // 如果构建缓存Result的时候出现取消操作，则删除缓存
            cache.remove(key, future);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    private FutureTask<Result> buildResult() {
        return new FutureTask<>(() -> {
            System.out.println("一个很耗时的构建过程...");
            return new Result();
        });
    }

    static class Result {
        int field1;
        int field2;
    }
}
