package com.lc.javase.jvm;

public class HelloWorld {
    public String sayHello(String name){
        ClassLoader classLoader = HelloWorld.class.getClassLoader();
        String hw = "hw";
        System.out.println(hw.getClass().getClassLoader());
        return "Hello " + name + ", i am loaded by " + classLoader + ".";
    }
}
