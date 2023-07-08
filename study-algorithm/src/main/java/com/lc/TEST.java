package com.lc;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;

/**
 * @author gujixian
 * @since 2023/6/13
 */
public class TEST {
    public static void main(String[] args) {
        System.out.println(new TEST().partition("aab"));
    }

    public List<List<String>> partition(String s) {
        if (s == null) {
            return null;
        }
        List<List<String>> result = new ArrayList<>();
        char[] cs = s.toCharArray();
        process(cs, 0, new ArrayList<>(), result);
        return result;
    }

    // 在 [0, i-1] 范围内已经分割好，并存在 palindromes 的情况下，完成 [i, N-1] 范围上的分割
    private void process(char[] cs, int i, List<String> palindromes, List<List<String>> result) {
        // base case
        if (i == cs.length) {
            result.add(new ArrayList<>(palindromes));
        }
        // j ∈ [i, N-1]，依次 “分割以 j 结尾，以 i 开头的回文串 + 后续”
        for (int j = i; j < cs.length; j++) {
            if (isPalindromes(cs, i, j)) {
                StringBuilder sb = new StringBuilder();
                for (int k = i; k <= j; k++) {
                    sb.append(cs[k]);
                }
                palindromes.add(sb.toString());
                // 后续
                process(cs, j+1, palindromes, result);
                palindromes.remove(palindromes.size() - 1);
            }
        }
    }

    private boolean isPalindromes(char[] cs, int i, int j) {
        while (i <= j && cs[i] == cs[j]) {
            i++;
            j--;
        }
        return i > j;
    }
}
