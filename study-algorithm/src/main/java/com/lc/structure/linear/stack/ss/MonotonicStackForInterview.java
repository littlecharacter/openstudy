package com.lc.structure.linear.stack.ss;

import com.alibaba.fastjson.JSON;
import javafx.util.Pair;

import java.util.*;

/**
 * 单调栈
 *
 * @author gujixian
 * @since 2022/12/22
 */
public class MonotonicStackForInterview<V> {
    // Pair：值 -> 索引队列
    private final Deque<Pair<V, Deque<Integer>>> stack = new LinkedList<>();
    private final Comparator<V> comparator;
    private final int capacity;
    private int size;

    public MonotonicStackForInterview(Comparator<V> comparator, int capacity) {
        this.comparator = comparator;
        this.capacity = capacity;
    }

    /**
     * 一个新的 (索引，值) 入栈，产生的特征值
     * @param i 索引
     * @param v 值
     * @return 特征值：index -> (L,R)（左开右开）
     */
    public Map<Integer, Pair<Integer, Integer>> push (int i, V v) {
        Map<Integer, Pair<Integer, Integer>> featureMap = new HashMap<>();
        // 1.小于栈顶元素，依次弹出所有满足条件的栈顶，并记录
        while (!stack.isEmpty() && comparator.compare(v, stack.peekLast().getKey()) < 0) {
            Pair<V, Deque<Integer>> element = stack.pollLast();
            int left = stack.isEmpty() ? -1 : stack.peekLast().getValue().peekLast();
            for (Integer index : element.getValue()) {
                featureMap.put(index, new Pair<>(left, i));
            }
        }
        // 2.大于栈顶元素
        if (stack.isEmpty() || comparator.compare(v, stack.peekLast().getKey()) > 0)  {
            Deque<Integer> indexStack = new LinkedList<>();
            indexStack.offerLast(i);
            stack.offerLast(new Pair<>(v, indexStack));
        } else {
            // 3.等于栈顶元素
            stack.peekLast().getValue().offerLast(i);
        }
        size++;
        // 数组遍历完了，对栈中剩余的每个元素求特征值
        if (size == capacity) {
            while (!stack.isEmpty()) {
                Pair<V, Deque<Integer>> element = stack.pollLast();
                int left = stack.isEmpty() ? -1 : stack.peekLast().getValue().peekLast();
                for (Integer index : element.getValue()) {
                    featureMap.put(index, new Pair<>(left, capacity));
                }
            }
        }
        return featureMap;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3,1,1,2,4};
        MonotonicStackForInterview<Integer> monotonicStack = new MonotonicStackForInterview<>((o1, o2) -> o1 - o2, nums.length);
        Map<Integer, Pair<Integer, Integer>> featureMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Map<Integer, Pair<Integer, Integer>> subFeatureMap = monotonicStack.push(i, nums[i]);
            if (!subFeatureMap.isEmpty()) {
                subFeatureMap.forEach(featureMap::put);
            }
        }
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println("\n0 1 2 3 4\n----------------");
        featureMap.forEach((index, pair) -> {
            System.out.println(index + ":[" + pair.getKey() + "," + pair.getValue() + "]");
        });
    }
}
