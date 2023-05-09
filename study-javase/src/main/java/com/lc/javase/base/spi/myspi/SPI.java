package com.lc.javase.base.spi.myspi;

/**
 * @author gujixian
 * @since 2023/5/10
 */
public class SPI {
    public static void main(String[] args) {
        // 获取默认扩展点
        // ExtensionPoint extension = ExtensionLoader.getExtensionLoader(ExtensionPoint.class).getExtension();
        // 获取自定义扩展点
        ExtensionPoint extension = ExtensionLoader.getExtensionLoader(ExtensionPoint.class).getExtension("B");
        extension.doSomething();
    }
}
