package com.lc.javase.base;

import com.google.common.base.Strings;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;

public class StringStudy {
    // 常量
    public static final String SFSX = "hello";
    // 类变量
    public static String ssx = "hello";
    // 实例变量
    private String sa1 = "hello";
    private String sa2 = "hello";
    private String sb1 = new String("hello");
    private String sb2 = new String("hello");

    public void study() {
        // 局部变量
        String msa1 = "hello";
        String msa2 = "hello";
        String msb1 = new String("hello");
        String msb2 = new String("hello");
    }

    public static void main(String[] args) throws Exception {
        // 这部分纯属乱卷，使用场景有限，跟编码的代码单元有关，象这种补充编码代码单元有两个，可以调试看value数组
        String xx = "中\uD834\uDD1E\uD834\uDD1E";
        System.out.println("xx=" + xx);
        System.out.println("xx=" + xx.length());
        System.out.println("xx=" + xx.codePointCount(0, xx.length()));

        // 不管怎么编码，只要 new 成 String，JDK 内部默认使用 UTF-16 编码
        String yy = new String(xx.getBytes("UTF-8"),"UTF-8");
        System.out.println("yy=" + yy);
        System.out.println("yy=" + yy.length());
        System.out.println("yy=" + yy.codePointCount(0, xx.length()));
        String zz = new String(xx.getBytes("UTF-32"),"UTF-32");
        System.out.println("zz=" + zz);
        System.out.println("zz=" + zz.length());
        System.out.println("zz=" + zz.codePointCount(0, xx.length()));


        StringStudy ss01 = new StringStudy();
        StringStudy ss02 = new StringStudy();
        System.out.println(ss01.sa1 + ":" + ss02.sa1);
        ss01.sa1 = ss02.sa1;
        ss01.sa1 = "world";
        System.out.println(ss01.sa1 + ":" + ss02.sa1);

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new String("HelloWorld");
        System.out.println(str2.intern() == str2);

        String str3 = new String("计算机");
        System.out.println(str3.intern() == str3);

        String str4 = new String("XXOO");
        String str5 = "XXOO";
        System.out.println("str4:" + System.identityHashCode(str4));
        System.out.println("str4.intern:" + System.identityHashCode(str4.intern()));
        System.out.println("str5:" + System.identityHashCode(str5));
        System.out.println(str4.intern() == str5);
    }
}
