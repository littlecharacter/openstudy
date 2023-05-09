package com.lc.javase.base.spi.myspi;

/**
 * @author gujixian
 * @since 2023/5/10
 */
@Extension(value = "A")
public interface ExtensionPoint {
    void doSomething();
}
