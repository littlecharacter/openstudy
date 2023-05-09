package com.lc.javase.base.spi;

/**
 * @author gujixian
 * @since 2023/5/9
 */
public class HelloServiceImplB implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("B: Hello World!");
    }
}