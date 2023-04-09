package com.lc.javase.collection;

import com.lc.javase.other.util.NumberUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class CollectionStudy {
    public void studyCollection() {
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("b", "b");
        treeMap.put("a", "a");
        treeMap.forEach((key, value) -> System.out.println(key + ":" + value));

        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        List<String> stringList = new ArrayList<>(16);
        Vector vector = new Vector();
        Stack<String> stringStack = new Stack<>();
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList();

        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();
    }

    public void studyArrayList() {
        ArrayList list = new ArrayList();
    }

    public void studyLinkedList() {
        LinkedList list = new LinkedList();
    }

    public void studyHashMap() {
        // 要求能容下 7 个元素，而不会扩容；装载因子为默认的 0.75f
        int capacity = 7;
        float loadFactor = 0.75f;
        // 计算需要传给 HashMap 的容量
        int initCapacity = Float.valueOf(capacity / loadFactor).intValue() + 1;
        // 实际创建 HashMap 的容量
        int realCapacity = NumberUtil.convertToExponentOfTwo(initCapacity);

        System.out.println("initCapacity = " + initCapacity + ", realCapacity = " + realCapacity);

        HashMap<Integer, Integer> map = new HashMap<>(initCapacity);
        // 常用方法
        map.put(1, 1);
        map.putIfAbsent(1, 1);
        map.getOrDefault(2, 0);
        map.remove(1);
        map.size();

        List<Object> objects = Collections.synchronizedList(new LinkedList<>());
    }

    public void studyConcurrentHashMap() {
        // 要求能容下 7 个元素，而不会扩容；装载因子为默认的 0.75f
        int capacity = 7;
        float loadFactor = 0.75f;
        // 计算需要传给 ConcurrentHashMap 的容量
        int initCapacity = Float.valueOf(capacity / loadFactor).intValue() + 1;
        // 实际创建 ConcurrentHashMap 的容量
        int realCapacity = NumberUtil.convertToExponentOfTwo(initCapacity);

        System.out.println("initCapacity = " + initCapacity + ", realCapacity = " + realCapacity);

        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(initCapacity);
        // 常用方法
        map.put(1, 1);
        map.putIfAbsent(1, 1);
        map.getOrDefault(2, 0);
        map.remove(1);
        map.mappingCount();
    }

    public void studyLinkedHashMap() {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        map.put(1, 1);
    }
}
