package com.lc.javase.jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 实现的功能同URLClassLoader，只不过把findClass暴露出来了
 * 改变双亲委托机制有两种方法：
 * 1、给自定义的parent类加载器传 null
 * 2、直接暴露findClass方法，调用findClass方法直接加载类
 */
public class MyClassLoader extends ClassLoader {
    private String path;

    public MyClassLoader(String path) {
        super(); // 用系统类加载器作为父类加载器
        this.path = path;
    }

    public MyClassLoader(ClassLoader parent, String path) {
        super(parent); // 用指定类加载器作为父类加载器
        this.path = path;
    }

    /**
     * 重写父类方法，返回一个Class对象
     * ClassLoader中对于这个方法的注释是:This method should be overridden by class loader implementations.
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // String filePath = path + File.separator + name.replace(".", File.separator) + ".class";
        String filePath = path + name.replaceAll("\\.", "/") + ".class";
        File file = new File(filePath);
        try {
            byte[] bytes = getClassBytes(file);
            return this.defineClass(name, bytes, 0, bytes.length);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private byte[] getClassBytes(File file) throws Exception {
        ByteArrayOutputStream baos;
        try (FileInputStream fis = new FileInputStream(file)) {
            FileChannel fc = fis.getChannel();
            baos = new ByteArrayOutputStream();
            try (WritableByteChannel wbc = Channels.newChannel(baos)) {
                ByteBuffer bb = ByteBuffer.allocate(1024);
                while (true) {
                    int i = fc.read(bb);
                    if (i == 0 || i == -1) {
                        break;
                    }
                    bb.flip();
                    wbc.write(bb);
                    bb.clear();
                }
            }
        }
        return baos.toByteArray();
    }

    public static void main(String[] args) throws Exception{
        ClassLoader classLoader = MyClassLoader.class.getClassLoader();
        while (classLoader.getParent() != null) {
            System.out.println("ClassLoader：" + classLoader);
            classLoader = classLoader.getParent();
        }
        System.out.println("ClassLoader：" + classLoader);

        MyClassLoader classpathClassLoader = new MyClassLoader("E:\\");
        Class classpathClazz = classpathClassLoader.loadClass("com.lc.javase.jvm.HelloWorld");
        Method classpathSayHello = classpathClazz.getDeclaredMethod("sayHello", String.class);
        System.out.println(classpathSayHello.invoke(classpathClazz.newInstance(), "world"));

        MyClassLoader customClassLoader = new MyClassLoader(null,"E:\\");
        Class customClazz = customClassLoader.loadClass("com.lc.javase.jvm.HelloWorld");
        Method customSayHello = customClazz.getDeclaredMethod("sayHello", String.class);
        System.out.println(customSayHello.invoke(customClazz.newInstance(), "world1"));

        customClassLoader = new MyClassLoader(null,"E:\\");
        customClazz = customClassLoader.loadClass("com.lc.javase.jvm.HelloWorld");
        customSayHello = customClazz.getDeclaredMethod("sayHello", String.class);
        System.out.println(customSayHello.invoke(customClazz.newInstance(), "world2"));

        HelloWorld helloWorld = new HelloWorld();
        System.out.println(helloWorld.sayHello("gujx"));
    }
}
