package com.lc.algorithm.dynamicprogramming;

import java.util.*;

/**
 * @author gujixian
 * @since 2023/5/13
 */
public class LC0017LetterCombinationsOfAPhoneNumber {
    private static final String[] map = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    private static final List<String> result = new ArrayList<>();
    private static final StringBuilder sb = new StringBuilder();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return Collections.emptyList();
        }
        char[] digitChars = digits.toCharArray();
        helper(digitChars, 0);
        return result;
    }

    private void helper(char[] digitChars, int index) {
        if (index == digitChars.length) {
            result.add(sb.toString());
            return;
        }
        char[] sc = map[digitChars[index] - '2'].toCharArray();
        for (int i = 0; i < sc.length; i++) {
            sb.append(sc[i]);
            helper(digitChars, index + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new LC0017LetterCombinationsOfAPhoneNumber().letterCombinations("2"));
    }
}
