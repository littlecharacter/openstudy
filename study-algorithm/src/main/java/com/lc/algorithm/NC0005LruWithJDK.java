package com.lc.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class NC0005LruWithJDK<K, V> extends LinkedHashMap<K, V> {
    /**
     * 定义缓存的容量
     */
    private int capacity;
    private static final long serialVersionUID = 1L;

    //带参数的构造器
    NC0005LruWithJDK(int capacity) {
        //调用LinkedHashMap的构造器，传入以下参数
        super(new Float(capacity / 0.75f).intValue() + 1, 0.75f, true);
        //传入指定的缓存最大容量
        this.capacity = capacity;
    }

    //实现LRU的关键方法，如果map里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        System.out.println("最老的元素={" + eldest.getKey() + ":" + eldest.getValue() + "}");
        return size() > capacity;
    }

    public static void main(String[] args) {
        // 指定缓存最大容量为4
        Map<Integer, String> cache = new NC0005LruWithJDK<>(4);
        // 先放满4个为净
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.put(4, "d");
        // 访问元素，把最近访问的元素移动到链表尾部
        cache.get(1);
        // 再放一个，并淘汰一个最近最少使用的（链表头节点）
        cache.put(5, "e");
        // 遍历结果
        System.out.print("遍历元素（链表从头至尾）：");
        for (Map.Entry<Integer, String> entry : cache.entrySet()) {
            System.out.print("{" + entry.getKey() + ":" + entry.getValue() + "} ");
        }
        System.out.print("\n遍历元素（链表从头至尾）：");
        cache.forEach((key, value) -> {
            System.out.print("{" + key + ":" + value + "} ");
        });
    }
}
