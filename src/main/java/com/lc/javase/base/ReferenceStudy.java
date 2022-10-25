package com.lc.javase.base;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author gujixian
 * @since 2022/10/25
 */
public class ReferenceStudy {
    public static void main(String[] args) {
        // testNormalReference();
        testSoftReference();
        // testWeakReference();
        // testPhantomReference();
    }

    // 强
    private static void testNormalReference() {
        // 略
    }

    // 软 -> 缓存、防止内存泄漏
    private static void testSoftReference() {
        SoftReference<byte[]> sr = new SoftReference<>(new byte[1024 * 1024 * 10]);
        sr.get()[0] = 5;
        System.out.println(sr.get());
        System.out.println(sr.get()[0]);
        byte[] bts = new byte[1024 * 1024 * 11];
        System.out.println(sr.get());
    }

    // 弱 -> 每次 GC 都会清除，没啥用
    private static void testWeakReference() {
        WeakReference<byte[]> wr = new WeakReference<>(new byte[1024 * 1024 * 10]);
        wr.get()[0] = 5;
        System.out.println(wr.get());
        System.out.println(wr.get()[0]);
        System.gc();
        System.out.println(wr.get());
    }

    // 虚 -> 形同虚设，没啥用
    private static void testPhantomReference() {
        // PhantomReference<byte[]> sr = new PhantomReference<>();

    }
}
