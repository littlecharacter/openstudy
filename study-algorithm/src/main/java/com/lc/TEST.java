package com.lc;

import sun.security.krb5.internal.crypto.HmacSha1Aes128CksumType;

import java.sql.Array;
import java.util.*;

/**
 * @author gujixian
 * @since 2023/6/13
 */
public class TEST {
    static class Suffix implements Comparable<Suffix> {
        int index;
        int rank;
        int next;
        public Suffix(int index, int rank, int next) {
            this.index = index;
            this.rank = rank;
            this.next = next;
        }
        @Override
        public int compareTo(Suffix other) {
            if (this.rank != other.rank) {
                return Integer.compare(this.rank, other.rank);
            }
            return Integer.compare(this.next, other.next);
        }
    }
    static int[] buildSuffixArray(int[] arr) {
        int n = arr.length;
        Suffix[] suffixes = new Suffix[n];
        // 创建后缀数组
        for (int i = 0; i < n; i++) {
            int rank = arr[i];
            int next = (i + 1 < n) ? arr[i + 1] : -1;
            suffixes[i] = new Suffix(i, rank, next);
        }
        // 将后缀数组按照字典序排序
        Arrays.sort(suffixes);
        int[] indexes = new int[n];
        for (int k = 4; k < 2 * n; k *= 2) {
            int rank = 0;
            int prevRank = suffixes[0].rank;
            suffixes[0].rank = rank;
            indexes[suffixes[0].index] = 0;
            // 更新排序后的后缀数组的rank值
            for (int i = 1; i < n; i++) {
                if (suffixes[i].rank == prevRank && suffixes[i].next == suffixes[i - 1].next) {
                    prevRank = suffixes[i].rank;
                    suffixes[i].rank = rank;
                } else {
                    prevRank = suffixes[i].rank;
                    suffixes[i].rank = ++rank;
                }
                indexes[suffixes[i].index] = i;
            }
            // 更新下一个排序所需的next值
            for (int i = 0; i < n; i++) {
                int nextIndex = suffixes[i].index + k / 2;
                suffixes[i].next = (nextIndex < n) ? suffixes[indexes[nextIndex]].rank : -1;
            }
            // 再次按照字典序排序
            Arrays.sort(suffixes);
        }
        // 构建最终的后缀数组
        int[] suffixArray = new int[n];
        for (int i = 0; i < n; i++) {
            suffixArray[i] = suffixes[i].index;
        }
        return suffixArray;
    }
    public static void main(String[] args) {
        int[] arr = {1,0,13,0,13,0};
        int[] suffixArray = buildSuffixArray(arr);
        System.out.println("后缀数组:");
        for (int i = 0; i < suffixArray.length; i++) {
            System.out.print(suffixArray[i] + " ");
        }
    }
}
