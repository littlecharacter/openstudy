package com.lc.javase.collect;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionStudy {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("H", "Hello");
        concurrentHashMap.put("W", "World");
        concurrentHashMap.put("W", "World");
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1,1);
    }
}
