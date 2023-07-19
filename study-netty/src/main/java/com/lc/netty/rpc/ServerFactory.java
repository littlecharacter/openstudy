package com.lc.netty.rpc;

import java.util.*;

/**
 * @author gujixian
 * @since 2023/7/18
 */
public class ServerFactory {
    // 服务签名 -> 服务主机集合
    Map<Class<?>, List<Server>> serverMap = new HashMap<>();
    public void iniServer() {
        // 从注册中心拉取服务签名
        // 启动 netty 客户端，注册 ReceiveHandler、连接服务端
        // 将 Server 放进 serverMap
    }
    public Server getServer(Class<?> clazz) {
        // 负载均衡算法
        return serverMap.get(clazz).get(0);
    }
}

