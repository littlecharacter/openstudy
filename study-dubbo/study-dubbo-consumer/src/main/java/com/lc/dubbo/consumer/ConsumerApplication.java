package com.lc.dubbo.consumer;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gujixian
 * @since 2023/5/9
 */
@SpringBootApplication
@EnableDubbo
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        ExtensionLoader extensionLoader;
    }
}
