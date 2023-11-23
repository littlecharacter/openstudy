package com.lc.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gujixian
 * @since 2023/7/7
 */
public class NC0005LruWithoutJDK<K, V> {
    private int size = 0;
    private final int capacity;

    // 哈希表
    private Map<K, ListNode<K, V>> cache;
    // 双向链表
    private LinkedList<K, V> list = new LinkedList<>();

    public NC0005LruWithoutJDK(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("should be more than 0.");
        }
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
    }

    public V put(K key, V value) {
        if (cache.containsKey(key)) {
            ListNode<K,V> node = cache.get(key);
            V old = node.value;
            node.value = value;
            list.moveNode(node);
            return old;
        }
        if (capacity == size) {
            ListNode<K, V> removeNode = list.removeHead();
            cache.remove(removeNode.key);
            size--;
        }
        ListNode<K,V> node = new ListNode<>(key, value);
        list.addNode(node);
        cache.put(key, node);
        size++;
        return node.value;
    }

    public V get(K key) {
        ListNode<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        list.moveNode(node);
        return node.value;
    }


    private static class LinkedList<K, V> {
        public ListNode<K, V> head = null;
        public ListNode<K, V> tail = null;

        public void addNode(ListNode<K, V> node) {
            if (tail == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = tail.next;
            }
        }

        public ListNode<K, V> removeHead() {
            ListNode<K, V> node = head;
            if (head == tail) { // 链表中只有一个节点的时候
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = null;
                node.next = null;
            }
            return node;
        }

        public void moveNode(ListNode<K, V> node) {
            // 移动节点在尾部
            if (node == tail) {
                return;
            }
            // 移动节点在头部
            if (node == head) {
                head = head.next;
                head.prev = null;

                tail.next = node;
                node.prev = tail;
                tail = tail.next;
                node.next = null;
                return;
            }
            // 移动节点在中间
            node.prev.next = node.next;
            node.next.prev = node.prev;

            tail.next = node;
            node.prev = tail;
            tail = tail.next;
            node.next = null;
        }
    }

    private static class ListNode<K, V> {
        public K key;
        public V value;
        public ListNode<K, V> prev;
        public ListNode<K, V> next;

        public ListNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        NC0005LruWithoutJDK<Integer, Integer> lRUCache = new NC0005LruWithoutJDK<>(2);
        System.out.print("null ");
        lRUCache.put(1, 1); // 缓存是 {1=1}
        System.out.print("null ");
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.print("null ");
        System.out.print(lRUCache.get(1) + " "); // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.print("null ");
        System.out.print(lRUCache.get(2) + " "); // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.print("null ");
        System.out.print(lRUCache.get(1) + " ");    // 返回 -1 (未找到)
        System.out.print(lRUCache.get(3) + " ");    // 返回 3
        System.out.print(lRUCache.get(4) + " ");    // 返回 4
        // null null null 1 null null null null 3 4
        System.out.println("\n-----------------------------");
        lRUCache = new NC0005LruWithoutJDK<>(2);
        lRUCache.put(2, 1);
        lRUCache.put(1, 1);
        lRUCache.put(2, 3);
        lRUCache.put(4, 1);
        System.out.print(lRUCache.get(1) + " ");
        System.out.print(lRUCache.get(2) + " ");
        // null 3
    }
}
