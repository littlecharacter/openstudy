package com.lc.javase.base;

public class BaseStudy {
    private static String name;

    private Long a = -129L;
    private Long b = -129L;

    public BaseStudy() {}

    public BaseStudy(String name) {
        BaseStudy.name = name;
    }

    public static void main(String[] args) throws Exception {
        BaseStudy baseStudy = new BaseStudy("123");
        System.out.println(BaseStudy.name);

        Class clazz = BaseStudy.class;
        clazz.getName();
        clazz.newInstance();
        // clazz.getMethod("xx").invoke();

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
    }

    public void eq() {
        System.out.println(a == b);
    }
}
