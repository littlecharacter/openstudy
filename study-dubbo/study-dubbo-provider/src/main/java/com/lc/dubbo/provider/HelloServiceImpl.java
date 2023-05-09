package com.lc.dubbo.provider;

import com.lc.dubbo.contract.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author gujixian
 * @since 2023/5/9
 */
@Service
@DubboService
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

    @Override
    public String getName(Long id) {
        return "name:" + id;
    }
}
