package com.lc;

/**
 * @author gujixian
 * @since 2023/6/13
 */
public class TEST {
    public static void main(String[] args) {
        System.out.println(new TEST().numTilings(60));
    }

    public int numTilings(int n) {
        if (n < 1) {
            return 0;
        }
        long result = process(3, 0, n);
        return (int)(result % 1000000007L);
    }

    // status：前一列的状态
    // index：当前列
    private long process(int status, int index, int n) {
        if (index == n) {
            return status == 3 ? 1L : 0L;
        }
        if (index > n) {
            return 0L;
        }
        long ways = 0L;
        if (status == 3) {
            ways += process(3, index + 1, n) + process(3, index + 2, n) + process(2, index + 2, n) + process(1, index + 2, n);
        } else if (status == 2) {
            ways += process(3, index + 1, n) + process(1, index + 1, n);
        } else if (status == 1) {
            ways += process(3, index + 1, n) + process(2, index + 1, n);
        }
        return ways;
    }
}
