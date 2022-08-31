package com.lc.javase;

import com.lc.javase.base.StringStudy;

import java.util.Date;

public class Test {
    // 常量
    public static final int SFIX = 127; // -128 int和long一样，关键是整数
    public static final int SFIY = 128; // -129
    public static final Integer SFI1 = 127;
    public static final Integer SFI2 = 128;
    public static final String SFS1 = "hello";
    public static final String SFS2 = new String("hello");
    public static final Date DATE1 = new Date();
    // 类变量
    public static int six = 127;
    public static int siy = 128;
    public static Integer si1 = 127;
    public static Integer si2 = 128;
    public static String ss1 = "hello";
    public static String ss2 = new String("hello");
    public static Date date2 = new Date();
    // 实例变量
    private int ix = 127;
    private int iy = 128;
    private Integer i1 = 127;
    private Integer i2 = 128;
    private String s1 = "hello";
    private String s2 = new String("hello");
    private Date date3 = new Date();

    public void test() {
        // 局部变量
        int mix = 127;
        int miy = 128;
        Integer mi1 = 127;
        Integer mi2 = 128;
        String ms1 = "hello";
        String ms2 = new String("hello");
        final String msx = "hw";
        // 基本类型只比较值，包括引用类型和基本类型比较
        System.out.println(Test.SFIY == Test.siy && Test.siy == this.iy && this.iy == miy);
        // 引用类型比较地址
        System.out.println(Test.SFI1 == Test.si1 && Test.si1 == this.i1 && this.i1 == mi1);
        System.out.println(Test.SFI2 == Test.si2 || Test.si2 == this.i2 || this.i2 == mi2);
        System.out.println(Test.SFS1 == Test.ss1 && Test.ss1 == this.s1 && this.s1 == ms1);
        System.out.println(Test.SFS1 == Test.SFS2 || Test.SFS2 == Test.ss2 || Test.ss2 == this.s2 || this.s2 == ms2);
    }

    public static void main(String[] args) {
        System.out.println(Test.DATE1);
        System.out.println(Test.date2);
        Test t = new Test();
        System.out.println(t.date3);
        t.test();
        while (true) {}
    }
}
