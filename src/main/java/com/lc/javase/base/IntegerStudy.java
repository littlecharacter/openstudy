package com.lc.javase.base;

import java.io.UnsupportedEncodingException;

/**
 * @author gujixian
 * @since 2022/8/12
 */
public class IntegerStudy {
    // 常量
    public static final int SFIX = 127; // int和long一样，关键是整数
    public static final int SFIY= 128;
    public static final Integer SFI1 = 127;
    public static final Integer SFI2 = 128;
    // 类变量
    public static int six = 127;
    public static int siy= 128;
    public static Integer si1 = 127;
    public static Integer si2 = 128;
    public static Integer i1 = 127;
    public static Integer i2 = 127;
    // 实例变量
    private int ix = 127;
    private int iy = 128;
    private Integer ia1 = 127; // -128
    private Integer ia2 = 127;
    private final Integer ib1 = 128; // -129
    private final Integer ib2 = 128;

    public void study(Integer y) {
        // 局部变量
        int mix = 127;
        int miy = 128;

        Integer mia1 = 127; // -128
        Integer mia2 = 127;
        Integer mib1 = 128; // -129
        Integer mib2 = 128;

        Integer x = mib1;
        x++;
        System.out.println(x + ":" + mib1);

        y++;
        System.out.println("y=" + y);
    }

    public static void main(String[] args) {
        IntegerStudy integerStudy = new IntegerStudy();
        integerStudy.study(IntegerStudy.SFIY);
        System.out.println(integerStudy.ib1 == integerStudy.ib2);
    }
}
