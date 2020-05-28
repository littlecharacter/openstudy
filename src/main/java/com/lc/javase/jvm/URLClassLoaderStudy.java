package com.lc.javase.jvm;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderStudy {
    public static void main(String[] args) throws Exception{
        File file = new File("E:/");
        URI uri = file.toURI();
        URL url = uri.toURL();

        URLClassLoader classpathClassLoader = new URLClassLoader(new URL[]{url});
        Class classpathClazz = classpathClassLoader.loadClass("com.lc.javase.jvm.HelloWorld");
        Method classpathSayHello = classpathClazz.getDeclaredMethod("sayHello", String.class);
        System.out.println(classpathSayHello.invoke(classpathClazz.newInstance(), "world"));

        URLClassLoader customClassLoader = new URLClassLoader(new URL[]{url}, null);
        Class customClazz = customClassLoader.loadClass("com.lc.javase.jvm.HelloWorld");
        Method customSayHello = customClazz.getDeclaredMethod("sayHello", String.class);
        System.out.println(customSayHello.invoke(customClazz.newInstance(), "world1"));

        customClassLoader = new URLClassLoader(new URL[]{url}, null);
        customClazz = customClassLoader.loadClass("com.lc.javase.jvm.HelloWorld");
        customSayHello = customClazz.getDeclaredMethod("sayHello", String.class);
        System.out.println(customSayHello.invoke(customClazz.newInstance(), "world2"));

        HelloWorld helloWorld = new HelloWorld();
        System.out.println(helloWorld.sayHello("gujx"));
    }
}
