package com.lc.structure.zother.slidingwindow.ss;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 滑动窗口：滑动窗口内的最大或最小更新结构
 *
 * @author gujixian
 * @since 2022/12/22
 */
public class SlidingWindowForInterview {
    private final Deque<Integer> window = new LinkedList<>();
    private final int capacity;
    private int head = 0;
    private int tail = 0;

    private final int[] nums;

    public SlidingWindowForInterview(int[] nums, int capacity) {
        this.nums = nums;
        this.capacity = capacity;
    }

    /**
     * slide 模拟窗口滑动，当窗口形成后，每次滑动都会产生特征值
     *
     * @param index, 滑入窗口的索引
     * @return 窗口内最大值的索引
     */
    public int slide(int index) {
        // 这里使用 "<0" 和 "<=0" 效果是一样的
        while (!window.isEmpty() && nums[window.peekLast()] < nums[index]) {
            window.pollLast();
        }
        window.offerLast(index);
        tail++; // tail 先+1，代表 [head,tail)，所以窗口大小就是 tail - head
        // 窗口形成，之后总是满足
        int feature = -1;
        if (tail - head == capacity) {
            feature = window.peekFirst();
            if (feature == head) {
                window.pollFirst();
            }
            head++;
        }
        return feature;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1,3,-1,-3,5,3,6,7};
        int k = 3;
        SlidingWindowForInterview win = new SlidingWindowForInterview(nums, k);
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            int feature = win.slide(i);
            if (feature > -1) {
                result[index++] = nums[feature];
            }
        }
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
}
