package com.lc;

/**
 * @author gujixian
 * @since 2023/6/13
 */
public class TEST {
    public static void main(String[] args) {
        int num = 123456789;
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000000; i++) {
            bitCount1(num);
        }
        long endTime = System.nanoTime();
        System.out.println("bitCount1 运行时间: " + (endTime - startTime) + " 纳秒");
        startTime = System.nanoTime();
        for (int i = 0; i < 10000000; i++) {
            bitCount2(num);
        }
        endTime = System.nanoTime();
        System.out.println("bitCount2 运行时间: " + (endTime - startTime) + " 纳秒");
    }
    public static int bitCount1(int i) {
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        return i & 0x3f;
    }
    public static int bitCount2(int i) {
        int count = 0;
        while (i != 0) {
            count++;
            i &= (i - 1);
        }
        return count;
    }
}
