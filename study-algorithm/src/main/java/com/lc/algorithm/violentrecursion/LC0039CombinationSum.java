package com.lc.algorithm.violentrecursion;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 题目：组合总和
 * 描述：
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，
 * 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 * 分析：暴力递归
 * 连接：https://leetcode.cn/problems/combination-sum/
 *
 * @author gujixian
 * @since 2023/5/13
 */
public class LC0039CombinationSum {
    public static void main(String[] args) {
        int[] candidates = new int[]{2, 3, 5};
        int target = 8;
        System.out.println(JSON.toJSONString(new LC0039CombinationSum().combinationSum(candidates, target)));
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);   // 对candidates数组进行排序
        List<List<Integer>> result = new ArrayList<>();
        helper(candidates, 0, target, new ArrayList<>(), result);
        return result;
    }

    private void helper(int[] candidates, int start, int target, List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {    // 如果target等于0，说明path中的数字和为target，将path加入到结果中
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < candidates.length; i++) {    // 遍历candidates数组
            if (candidates[i] > target) {
                return;
            }
            path.add(candidates[i]);    // 将candidates[i]加入到path中
            helper(candidates, i, target - candidates[i], path, result);  // 继续递归调用helper函数
            path.remove(path.size() - 1);   // 回溯，将candidates[i]从path中移除
        }
    }
}
