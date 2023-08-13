package com.lc.javase.base.reflection;

import java.lang.reflect.Method;

/**
 * 去查看字节码
 *
 * @author gujixian
 * @since 2023/8/13
 */
public class ReflectionStudy {
    public static void main(String[] args) throws Exception {
        Class<ReflectionStudy> clazz = ReflectionStudy.class;
        Method helloMethod = clazz.getMethod("hello", String.class);
        System.out.println(helloMethod.invoke(clazz.newInstance(), "World"));
    }

    public String hello(String name) {
        return "Hello " + name;
    }
}
