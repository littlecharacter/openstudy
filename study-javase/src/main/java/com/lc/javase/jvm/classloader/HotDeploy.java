package com.lc.javase.jvm.classloader;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 注意：单纯使用自定义 ClassLoader 是不能实现无业务入侵的热部署的！ -> 可以使用 java 的 Instrumentation API 来实现
 *
 * @author gujixian
 * @since 2023/5/9
 */
public class HotDeploy {
    public static void main(String[] args) throws Exception {
        while (true) {
            MyClassLoader myClassLoader = new MyClassLoader(null, "E:\\JetBrains\\IntelliJIdea\\Studyspace\\openstudy\\study-javase\\target\\classes\\");
            Class<?> clazz = myClassLoader.loadClass("com.lc.javase.jvm.classloader.HotDeploy$HelloWorld");
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("sayHi");
            method.invoke(obj);
            TimeUnit.SECONDS.sleep(3);
        }
    }

    public static class HelloWorld {
        public void sayHi() {
            System.out.println("2");
        }
    }
}
