package com.lc.javase.base;

public class StringStudy {
    public static void main(String[] args) {
        final String  s1 ;
        String s2 = "vv";
        String s3 = "vvww";
        String s4 = "vv"+"ww";
        s1 = "ww";
        String s5 = "vv"+s1;
        String s6 = s2+s1;
        System.out.println(s3 == s4);//true
        System.out.println(s3 == s5);//false

        String s7 = "中国";
        String s8 = "china";
        System.out.println(s7 + ":" + s7.length());
        System.out.println(s8 + ":" + s8.length());

        String s9 = "yop@daojia-inc.com";
        String s10 = "1234567890";
        String[] s9s = s9.split("@");
        String[] s10s = s10.split("@");
        System.out.println(s9 + "：" + s9s[0]);
        System.out.println(s10 + "：" + s10s[0]);
    }
}
