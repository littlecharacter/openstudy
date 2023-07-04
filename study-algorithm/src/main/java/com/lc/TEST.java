package com.lc;

import com.alibaba.fastjson.JSON;

import java.util.Random;

/**
 * @author gujixian
 * @since 2023/6/13
 */
public class TEST {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new TEST().quickSort(new int[]{3, 5, 1, 6, 2, 8, 4, 2, 4, 3})));
    }

    public int[] quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void quickSort(int[] nums, int s, int e) {
        // base case
        if (s >= e) {
            return;
        }
        // 选轴
        int pivot = nums[new Random().nextInt(e - s) + s];
        // 归边
        int l = s - 1;
        int r = e + 1;
        int i = s;
        while (i < r) {
            if (nums[i] < pivot) {
                swap(nums, ++l, i++);
            } else if (nums[i] > pivot) {
                swap(nums, i, --r);
            } else {
                i++;
            }
        }
        quickSort(nums, s, l);
        quickSort(nums, r, e);
    }

    private void swap(int[] nums, int i, int j) {
        // 判断同一位置不交换很重要，数组同一个位置交换会抹成 0 -> 要想同一个位置也交换就用最常用的 -> 引入一个额外变量
        if (i == j) {
            return;
        }
        nums[i] = nums[i] ^ nums[j];
        nums[j] = nums[i] ^ nums[j];
        nums[i] = nums[i] ^ nums[j];
    }
}
