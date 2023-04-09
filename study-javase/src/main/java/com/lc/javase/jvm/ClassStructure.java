package com.lc.javase.jvm;

public class ClassStructure {
    public static final String HELLO = "Hello";
    public static final String WORLD = "World";
    public String sayHello(String name) {
        int id = 1;
        return name + "(" + id + "):" + HELLO + WORLD;
    }
}
