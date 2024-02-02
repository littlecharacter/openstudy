package com.lc.javase.grow.compare;

import javafx.util.Pair;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gujixian
 * @since 2024/2/2
 */
public class FutureCenter {
    private static final Map<Long, Pair<CompletableFuture<Object>, CompletableFuture<Object>>> FUTURE_CENTER = new ConcurrentHashMap<>();

    private FutureCenter() {}

    public static void addSource(long requestId, CompletableFuture<Object> cf) {
        FUTURE_CENTER.putIfAbsent(requestId, new Pair<>(cf, null));
    }

    public static void addTarget(long requestId, CompletableFuture<Object> cf) {
        FUTURE_CENTER.put(requestId, new Pair<>(FUTURE_CENTER.get(requestId).getKey(), cf));
    }

    public static Pair<CompletableFuture<Object>, CompletableFuture<Object>> getFuture(long requestId) {
        return FUTURE_CENTER.get(requestId);
    }
}
