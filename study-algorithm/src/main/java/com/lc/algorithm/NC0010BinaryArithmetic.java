package com.lc.algorithm;

import com.lc.TEST;

/**
 * @author gujixian
 * @since 2023/7/4
 */
public class NC0010BinaryArithmetic {
    // a + b = (a ^ b) + ((a & b) << 1)，循环直到进位信息为 0
    //        无进位相加       进位信息
    public int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    // a - b = a + (-b)
    public int minus(int a, int b) {
        return add(a, negative(b));
    }

    // 跟十进制的乘法一样，列出竖乘式就明白了
    public int multi(int a, int b) {
        int result = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                result = add(result, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return result;
    }

    // 考虑越界的乘法
    public int multiply(int a, int b) {
        long x = a;
        long y = b;
        long result = 0;
        while (y != 0) {
            if ((y & 1) != 0) {
                result = result + x;
            }
            x <<= 1;
            y >>>= 1;
        }
        if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) result;
    }

    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        if (divisor == 1) {
            return dividend;
        }
        int dd = dividend > 0 ? negative(dividend) : dividend;
        int dr = divisor > 0 ? negative(divisor) : divisor;
        boolean flag = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0);
        if (dd > dr) {
            return 0;
        }
        int result = 0;
        while (dd <= dr) {
            int value = dr;
            int count = 1;
            // 1) value > Integer.MIN_VALUE >> 1 ：防止 value+value 越界
            // 2) 当 value << 1 小于 dd 时，就不能继续一位了，如果继续就过了【向下取整】
            //    例如，dd = 10， value = 3，value 只能右移动 1 次，不然超过了 10
            //    0000 1010
            //    0000 0011
            while (value >= Integer.MIN_VALUE >> 1 && dd <= value << 1) {
                count <<= 1;
                value <<= 1;
            }
            result |= count;
            dd = minus(dd, value);
        }
        return flag ? result : -result;
    }

    private int negative(int n) {
        return add(~n, 1);
    }

    private boolean isNegative(int n) {
        return n < 0;
    }


    public static void main(String[] args) {
        TEST test = new TEST();
        System.out.println(test.divide(9, 2));
        System.out.println(test.divide(Integer.MIN_VALUE, 2));
    }
}
