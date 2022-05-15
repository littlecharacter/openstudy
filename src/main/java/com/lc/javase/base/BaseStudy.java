package com.lc.javase.base;

public class BaseStudy {
    private static String name;

    public BaseStudy(String name) {
        BaseStudy.name = name;
    }

    public static void main(String[] args) throws Exception {
        BaseStudy baseStudy = new BaseStudy("123");
        System.out.println(BaseStudy.name);

        Class clazz = BaseStudy.class;
        clazz.newInstance();
        // clazz.getMethod("xx").invoke();
    }
}
