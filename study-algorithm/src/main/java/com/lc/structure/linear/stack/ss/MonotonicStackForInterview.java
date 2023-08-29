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
public class MonotonicStackForInterview {
    // Pair：值 -> 索引队列
    private final Deque<Pair<Integer, Deque<Integer>>> stack = new LinkedList<>();

    public int[][] buildWithoutEqual (int[] nums) {
        int[][] help = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            // 1.栈顶元素大于入栈元素 / 栈顶元素大于等于入栈元素
            while (!stack.isEmpty() && stack.peekLast().getKey() > nums[i]) {
                Deque<Integer> indexStack = stack.pollLast().getValue();
                int left = stack.peekLast() == null ? -1 : stack.peekLast().getValue().peekLast();
                for (int index : indexStack) {
                    help[index] = new int[]{left, i};
                }
            }
            // 2.栈顶元素等于入栈元素
            if (!stack.isEmpty() && stack.peekLast().getKey() == nums[i]) {
                stack.peekLast().getValue().offerLast(i);
                continue;
            }
            // 3.栈为空 or 栈顶元素小于入栈元素
            Deque<Integer> indexStack = new LinkedList<>();
            indexStack.offerLast(i);
            stack.offerLast(new Pair<>(nums[i], indexStack));
        }
        // 弹出栈中存留元素
        while (!stack.isEmpty()) {
            Deque<Integer> indexStack = stack.pollLast().getValue();
            int left = stack.peekLast() == null ? -1 : stack.peekLast().getValue().peekLast();
            for (int index : indexStack) {
                help[index] = new int[]{left, nums.length};
            }
        }
        return help;
    }

    public int[][] buildWithEqual (int[] nums) {
        int[][] help = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            // 1.栈顶元素大于入栈元素 / 栈顶元素大于等于入栈元素
            while (!stack.isEmpty() && stack.peekLast().getKey() >= nums[i]) {
                Deque<Integer> indexStack = stack.pollLast().getValue();
                int left = stack.peekLast() == null ? -1 : stack.peekLast().getValue().peekLast();
                for (int index : indexStack) {
                    help[index] = new int[]{left, i};
                }
            }
            // // 2.栈顶元素等于入栈元素
            // if (!stack.isEmpty() && stack.peekLast().getKey() == nums[i]) {
            //     stack.peekLast().getValue().offerLast(i);
            //     continue;
            // }
            // 3.栈为空 or 栈顶元素小于入栈元素
            Deque<Integer> indexStack = new LinkedList<>();
            indexStack.offerLast(i);
            stack.offerLast(new Pair<>(nums[i], indexStack));
        }
        // 弹出栈中存留元素
        while (!stack.isEmpty()) {
            Deque<Integer> indexStack = stack.pollLast().getValue();
            int left = stack.peekLast() == null ? -1 : stack.peekLast().getValue().peekLast();
            for (int index : indexStack) {
                help[index] = new int[]{left, nums.length};
            }
        }
        return help;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3,1,1,2,4};
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println("\n0 1 2 3 4\n----------------");
        MonotonicStackForInterview monotonicStack = new MonotonicStackForInterview();
        int[][] help = monotonicStack.buildWithoutEqual(nums);
        for (int i = 0; i < help.length; i++) {
            System.out.println(i + ":[" + help[i][0] + "," + help[i][1] + "]");
        }
        System.out.println("----------------");
        help = monotonicStack.buildWithEqual(nums);
        for (int i = 0; i < help.length; i++) {
            System.out.println(i + ":[" + help[i][0] + "," + help[i][1] + "]");
        }
    }
}
