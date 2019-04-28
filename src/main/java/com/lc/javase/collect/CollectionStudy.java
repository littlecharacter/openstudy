package com.lc.javase.collect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionStudy {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("H", "Hello");
        concurrentHashMap.put("W", "World");
        concurrentHashMap.put("W", "World");
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1,1);
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("b", "b");
        treeMap.put("a", "a");
        treeMap.forEach((key, value) -> System.out.println(key + ":" + value));
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        List<String> stringList = new ArrayList<>(16);
        Vector vector = new Vector();
        Stack<String> stringStack = new Stack<>();
    }
}
