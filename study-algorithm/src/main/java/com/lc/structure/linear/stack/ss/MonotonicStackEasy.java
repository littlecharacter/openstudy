package com.lc.structure.linear.stack.ss;

import javafx.util.Pair;

import java.util.*;

/**
 * 单调栈
 *
 * @author gujixian
 * @since 2022/12/22
 */
public class MonotonicStackEasy<V> {
    // Pair：值 -> 索引栈
    private final Deque<Pair<V, Deque<Integer>>> stack = new LinkedList<>();
    private final Comparator<V> comparator;
    private final int capacity;
    private int size;

    public MonotonicStackEasy(Comparator<V> comparator, int capacity) {
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
        /**
         * 有时候需要利用单调栈得到的结构进一步求解，为了避免重复，这里改成 <= 即可（第二个分支删除），
         * 即得到一个(...]（左开右闭）的范围，
         * 当然 “索引栈” 也可以换成整数：Deque<Pair<V, Deque<Integer>>> --> Deque<Pair<V, Integer>>
         */
        // 1.小于栈顶元素，依次弹出所有满足条件的栈顶，并记录
        while (!stack.isEmpty() && comparator.compare(v, stack.peekLast().getKey()) < 0) {
            Pair<V, Deque<Integer>> element = stack.pollLast();
            int left = stack.isEmpty() ? -1 : stack.peekLast().getValue().peekLast();
            for (Integer index : element.getValue()) {
                featureMap.put(index, new Pair<>(left, i));
            }
        }
        // 2.等于栈顶元素
        if (!stack.isEmpty() && comparator.compare(v, stack.peekLast().getKey()) == 0) {
            stack.peekLast().getValue().offerLast(i);
        } else {
            // 3.大于栈顶元素
            Deque<Integer> indexStack = new LinkedList<>();
            indexStack.offerLast(i);
            stack.offerLast(new Pair<>(v, indexStack));
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
        MonotonicStackEasy<Integer> monotonicStack = new MonotonicStackEasy<>((o1, o2) -> o1 - o2, nums.length);
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
