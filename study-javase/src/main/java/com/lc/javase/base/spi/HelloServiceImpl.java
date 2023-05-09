package com.lc.javase.base.spi;

/**
 * @author gujixian
 * @since 2023/5/9
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
}