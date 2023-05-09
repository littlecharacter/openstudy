package com.lc.javase.base.spi.myspi;

/**
 * @author gujixian
 * @since 2023/5/10
 */
public class ExtensionPointB implements ExtensionPoint{
    @Override
    public void doSomething() {
        System.out.println("B: do something...");
    }
}
