package com.lc.javase.other.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author gujixian
 * @version 1.0
 * @date 2020-04-01
 */
public class NumberUtil {
    /**
     * 保留2位小数
     */
    public static final int SCALE_2 = 2;
    /**
     * 保留3位小数
     */
    public static final int SCALE_3 = 3;
    /**
     * 一百,用来做除数,精确到分
     */
    public static final int HUNDRED = 100;
    /**
     * 一千,用来做除数,精确到厘
     */
    public static final int THOUSAND = 1000;

    private static final String THOUSAND_STRING = "1000";

    /**
     * 将任意数转换成int
     *
     * @param number, 入参
     * @param <T>,    Number的子类
     * @return int
     * @author gujixian
     * @date 2020-04-01
     */
    public static <T extends Number> int convertToInt(T number) {
        return number == null ? 0 : number.intValue();
    }

    /**
     * 将任意数转换成long
     *
     * @param number, 入参
     * @param <T>,    Number的子类
     * @return long
     */
    public static <T extends Number> long convertToLong(T number) {
        return number == null ? 0L : number.longValue();
    }

    /**
     * 将任意数转换成float
     *
     * @param number, 入参
     * @param <T>,    Number的子类
     * @return float
     */
    public static <T extends Number> float convertToFloat(T number) {
        return number == null ? 0F : number.floatValue();
    }

    /**
     * 将任意数转换成double
     *
     * @param number, 入参
     * @param <T>,    Number的子类
     * @return double
     */
    public static <T extends Number> double convertToDouble(T number) {
        return number == null ? 0D : number.doubleValue();
    }

    /**
     * 将任意数转换成BigDecimal
     *
     * @param number, 入参
     * @param <T>,    Number的子类
     * @return BigDecimal
     */
    public static <T extends Number> BigDecimal convertToBigDecimal(T number) {
        return number == null ? BigDecimal.ZERO : new BigDecimal(number.toString());
    }

    /**
     * 将一个整数转换成刚大于等于它的2的n次幂值
     *
     * @param capacity, 给定的值
     * @return int
     * @author gujixian
     * @date 2020-04-01
     */
    public static int convertToExponentOfTwo(int capacity){
        int n = capacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    public static long mulBy1000(Double param) {
        return param == null ? 0 : convertToBigDecimal(param).multiply(new BigDecimal(THOUSAND_STRING)).longValue();
    }

    public static long mulBy1000(BigDecimal param) {
        return param == null ? 0 : param.multiply(new BigDecimal(THOUSAND_STRING)).longValue();
    }

    /**
     * 数据相加
     *
     * @param param1 如果param1是null,将被认为是0
     * @param param2 如果param2是null,将被认为是0
     * @return
     */
    public static long add(Long param1, Long param2) {
        return convertToLong(param1) + convertToLong(param2);
    }

    /**
     * 数据相加
     *
     * @param params
     * @return
     */
    public static long addMul(Long... params) {
        return Arrays.stream(params).filter(Objects::nonNull).reduce(0L, (p1, p2) -> p1 + p2);
    }

    /**
     * 相减
     *
     * @param param1 如果param1是null,将被认为是0
     * @param param2 如果param2是null,将被认为是0
     * @return
     */
    public static long subtract(Long param1, Long param2) {
        return convertToLong(param1) - convertToLong(param2);
    }

    /**
     * 乘
     *
     * @param number1
     * @param number2
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal multiply(T number1, T number2) {
        BigDecimal bd1 = convertToBigDecimal(number1);
        BigDecimal bd2 = convertToBigDecimal(number2);
        return bd1.multiply(bd2);
    }

    /**
     * 乘-默认保留两位小数，四舍五入
     *
     * @param number1
     * @param number2
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal multiplyWithRound(T number1, T number2) {
        BigDecimal bd1 = convertToBigDecimal(number1);
        BigDecimal bd2 = convertToBigDecimal(number2);
        return bd1.multiply(bd2).setScale(SCALE_3, RoundingMode.HALF_UP);
    }

    /**
     * 乘-保留位数和舍入规则由参数控制
     *
     * @param number1
     * @param number2
     * @param scale
     * @param roundMode
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal multiplyWithRound(T number1, T number2, int scale, RoundingMode roundMode) {
        BigDecimal bd1 = convertToBigDecimal(number1);
        BigDecimal bd2 = convertToBigDecimal(number2);
        return bd1.multiply(bd2).setScale(scale, roundMode);
    }

    /**
     * 除
     *
     * @param number1
     * @param number2
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal divide(T number1, T number2) {
        BigDecimal bd1 = convertToBigDecimal(number1);
        BigDecimal bd2 = convertToBigDecimal(number2);
        if (bd2.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return bd1.divide(bd2);
    }

    /**
     * 除-默认保留两位小数，四舍五入
     *
     * @param number1
     * @param number2
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal divideWithRound(T number1, T number2) {
        BigDecimal bd1 = convertToBigDecimal(number1);
        BigDecimal bd2 = convertToBigDecimal(number2);
        if (bd2.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO.setScale(SCALE_3, RoundingMode.HALF_UP);
        }
        return bd1.divide(bd2).setScale(SCALE_3, RoundingMode.HALF_UP);
    }

    /**
     * 除-保留位数和舍入规则由参数控制
     *
     * @param number1
     * @param number2
     * @param scale
     * @param roundMode
     * @param <T>
     * @return
     */
    public static <T extends Number> BigDecimal divideWithRound(T number1, T number2, int scale, RoundingMode roundMode) {
        BigDecimal bd1 = convertToBigDecimal(number1);
        BigDecimal bd2 = convertToBigDecimal(number2);
        if (bd2.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO.setScale(scale, roundMode);
        }
        return bd1.divide(bd2).setScale(scale, roundMode);
    }
}