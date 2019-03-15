package com.lc.javase.jvm;

public class HelloWorld {
    public String sayHello(String name){
        ClassLoader classLoader = HelloWorld.class.getClassLoader();
        return "Hello " + name + ", i am loaded by " + classLoader + ".";
    }
}
