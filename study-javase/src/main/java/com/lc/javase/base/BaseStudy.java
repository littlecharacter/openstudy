package com.lc.javase.base;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class BaseStudy {
    private static String name;

    private Long a = -129L;
    private Long b = -129L;

    public BaseStudy() {}

    public BaseStudy(String name) {
        BaseStudy.name = name;
    }

    public static void main(String[] args) throws Exception {
        BaseStudy BaseStudy = new BaseStudy("123");
        System.out.println(BaseStudy.name);

        Class clazz = BaseStudy.class;
        clazz.getName();
        clazz.newInstance();
        // clazz.getMethod("xx").invoke();
        Class<? extends Class> aClass = clazz.getClass();
        System.out.println(clazz);
        System.out.println(aClass);
        System.out.println(aClass.getClass());

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        ReferenceQueue referenceQueue = null;
        PhantomReference x = new PhantomReference(new Object(), referenceQueue);
        ;


    }

    public void eq() {
        System.out.println(a == b);
    }
}
