package com.lc.structure.zother.slidingwindow.ss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 滑动窗口：滑动窗口内的最大或最小更新结构
 *
 * @author gujixian
 * @since 2022/12/22
 */
public class SlidingWindow<E> {
    private final Deque<Node<E>> window = new LinkedList<>();
    private final Comparator<E> comparator;
    private final int capacity;
    private int head;
    private int tail;

    public SlidingWindow(Comparator<E> comparator, int capacity) {
        this.comparator = comparator;
        this.capacity = capacity;
    }

    /**
     * slide 模拟窗口滑动，当窗口形成后，每次滑动都会产生特征值
     *
     * @param node
     * @return
     */
    public Node<E> slide(Node<E> node) {
        while (!window.isEmpty() && comparator.compare(window.peekLast().getElement(), node.getElement()) < 0) {
            window.pollLast();
        }
        window.offerLast(node);
        tail++; // tail 先+1，代表 [head,tail)，所以窗口大小就是 tail - head
        // 窗口形成，之后总是满足
        Node<E> feature = null;
        if (tail - head == capacity) {
            feature = window.peekFirst();
            assert feature != null;
            if (feature.index == head) {
                window.pollFirst();
            }
            head++;
        }
        return feature;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Node<E> {
        private int index;
        private E element;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,3,-1,-3,5,3,6,7};
        int k = 3;
        SlidingWindow<Integer> win = new SlidingWindow<>((o1, o2) -> o1 - o2, k);
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            Node<Integer> max = win.slide(new Node<>(i, nums[i]));
            if (max != null) {
                result[index++] = max.getElement();
            }
        }
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
}
