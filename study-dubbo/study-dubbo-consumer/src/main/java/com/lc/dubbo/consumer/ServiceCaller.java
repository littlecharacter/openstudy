package com.lc.dubbo.consumer;

import com.lc.dubbo.contract.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author gujixian
 * @since 2023/5/9
 */
@Component
public class ServiceCaller implements CommandLineRunner {
    @DubboReference
    HelloService helloService;

    @Override
    public void run(String... args) throws Exception {
        for (;;) {
            System.out.println(helloService.sayHello("gujixian"));
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
