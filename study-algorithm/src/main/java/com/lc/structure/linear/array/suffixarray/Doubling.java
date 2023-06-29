package com.lc.structure.linear.array.suffixarray;

import java.util.Arrays;

/**
 * 倍增法求后缀数组
 * rank1：所有后缀前 1 个位置的排名
 * rank2：所有后缀前 2 个位置的排名
 * rank4：所有后缀前 4 个位置的排名
 * ...
 * rankM：所有后缀前 M 个位置的排名
 * rank1 -> rank2 -> rank4 -> rank8 -> ... -> rankM
 * 1 <= M < N*2（N为原数组的长度）【比如，N=6，rank4（继续） -> rank8（继续，包含rank6）-> rank16（终止，M < N * 2）】
 * <p>
 * 举个例子：[b,a,n,a,n,a]（[1,0,13,0,13,0]）
 * rank1：[b, a, n , a, n, a] --排名--> [1, 0, 13, 0, 13, 0]（rank1 的排名就是原数组，好理解吧）
 * -> [(1,0), (0,13), (13,0), (0,13), (13,0), (0,-1)]（得到 rank2 排名的依据）（M=2）
 * rank2：[ba, an, na, an, na, a#] --排名--> [3, 2, 4, 2, 4, 1]
 * -> [(3,4), (2,2), (4,4), (2,1), (4,-1), (1,-1)]（得到 rank4 排名的依据）（M=4）
 * 先比较前 2 个（rank2[i]），再比较后 2 个（rank2[i+2]）（rank2中有每个位置开头的前 2 = M / 2 个字符的比较）
 * ● bana：rank2[0] 和 rank2[0+2]，i=0
 * ● anan：rank2[1] 和 rank2[1+2]，i=1
 * ● nana：rank2[2] 和 rank2[2+2]，i=2
 * ● ana#：rank2[3] 和 rank2[3+2]，i=3
 * ● na##：rank2[4] 和 rank2[4+2]，i=4
 * ● a###：rank2[5] 和 rank2[5+2]，i=5
 * rank4：[bana, anan, nana, ana#, na##, a###] --排名--> [4,3,6,2,5,1]
 * ...
 * 直到 rankM
 *
 * 注意：过程中必须保留相同排名，不然结果可能不正确
 *
 * 后缀数组：sa[i] 表示将所有后缀排序（字典序由小到大）后第 i 小的后缀的编号（原数组下标）
 * 排名数组：rank[i] 表示后缀 i 的排名，是重要的辅助数组，
 * 高度数组：height[i] = lcp(sa[i], sa[i-1])，即第 i 名的后缀与它前一名的后缀的最长公共前缀的长度（LCP），height[0] = 0
 *
 * @author gujixian
 * @since 2023/6/29
 */
public class Doubling {
    public int[] sa(int[] nums) {
        int n = nums.length;
        // 如果 nums[i] < 0，那么需要将数组重新处理一下
        int min = 0;
        for (int num : nums) {
            if (num < min) {
                min = num;
            }
        }
        if (min < 0) {
            for (int num : nums) {
                num += -min;
            }
        }
        // 创建并初始化后缀数组（k=2）
        Suffix[] suffixes = new Suffix[n];
        for (int i = 0; i < n; i++) {
            int rank = nums[i];
            int next = (i + 1 < n) ? nums[i + 1] : -1;
            suffixes[i] = new Suffix(i, rank, next);
        }
        // 将后缀数组按照字典序排序
        Arrays.sort(suffixes);
        for (int m = 4; m < n * 2; m *= 2) {
            // sa[i] 表示以 i 为起始位置的后缀的排名
            int[] sa = new int[n];
            // rank 和 preRank，处理相同排名用
            int rank = 0;
            int preRank = suffixes[0].rank;
            suffixes[0].rank = rank;
            sa[suffixes[0].index] = 0;
            // 过程中必须保留相同排名，不然结果可能不正确
            for (int i = 1; i < n; i++) {
                if (suffixes[i].rank == preRank && suffixes[i].next == suffixes[i - 1].next) {
                    preRank = suffixes[i].rank;
                    suffixes[i].rank = rank;
                } else {
                    preRank = suffixes[i].rank;
                    suffixes[i].rank = ++rank;
                }
                sa[suffixes[i].index] = i;
            }
            for (Suffix suffix : suffixes) {
                int nextIndex = suffix.index + m / 2;
                suffix.next = (nextIndex < n) ? suffixes[sa[nextIndex]].rank : -1;
            }
            // 循环将后缀数组按照字典序排序
            Arrays.sort(suffixes);
        }
        // 构建最终的后缀数组
        int[] suffixArray = new int[n];
        for (int i = 0; i < n; i++) {
            suffixArray[i] = suffixes[i].index;
        }
        return suffixArray;
    }

    public int[] rank(int[] sa) {
        int[] rank = new int[sa.length];
        for (int i = 0; i < sa.length; i++) {
            rank[sa[i]] = i;
        }
        return rank;
    }

    public int[] height(int[] nums, int[] sa) {
        int[] rank = this.rank(sa);
        int n = nums.length;
        int[] height = new int[n];
        for (int i = 0, k = 0; i < n; ++i) {
            if (rank[i] != 0) {
                if (k > 0) {
                    --k;
                }
                int j = sa[rank[i] - 1];
                while (i + k < n && j + k < n && nums[i + k] == nums[j + k]) {
                    ++k;
                }
                height[rank[i]] = k;
            }
        }
        return height;
    }

    private static class Suffix implements Comparable<Suffix> {
        // 后缀的开始位置
        int index;
        // 后缀的排名
        int rank;
        // 下一个后缀的排名：若比较的是后缀的前 M 个位置，则 next 表示以(index + M / 2) 为起始位置的后缀的排名
        int next;

        public Suffix(int index, int rank, int next) {
            this.index = index;
            this.rank = rank;
            this.next = next;
        }

        @Override
        public int compareTo(Suffix suffix) {
            if (this.rank != suffix.rank) {
                return Integer.compare(this.rank, suffix.rank);
            }
            return Integer.compare(this.next, suffix.next);
        }
    }


    public static void main(String[] args) {
        // banana
        String s = "banana";
        char[] chars = s.toCharArray();
        int[] nums = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            nums[i] = chars[i] - 'A';
        }
        int[] sa = new Doubling().sa(nums);
        System.out.println("后缀数组:");
        for (int i = 0; i < sa.length; i++) {
            System.out.print(sa[i] + " ");
        }
    }
}
