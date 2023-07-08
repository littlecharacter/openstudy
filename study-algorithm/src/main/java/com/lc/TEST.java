package com.lc;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;

/**
 * @author gujixian
 * @since 2023/6/13
 */
public class TEST {
    public static void main(String[] args) {}

    public boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        int count = 0;
        Map<Character, Integer> map = new HashMap<>();
        char[] scs = s.toCharArray();
        char[] tcs = t.toCharArray();
        for (char sc : scs) {
            map.put(sc, map.getOrDefault(sc, 0) + 1);
            count++;
        }
        for (char tc : tcs) {
            if (map.getOrDefault(tc, 0) > 0) {
                map.put(tc, map.get(tc) - 1);
                count--;
            }
        }
        return count == 0;
    }
}
