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
    }
}
