package com.lc.javase.base.spi;

import java.util.ServiceLoader;

/**
 * @author gujixian
 * @since 2023/5/10
 */
public class SPI {
    public static void main(String[] args) {
        ServiceLoader<HelloService> loader = ServiceLoader.load(HelloService.class);
        for (HelloService service : loader) {
            service.sayHello();
        }
    }
}
