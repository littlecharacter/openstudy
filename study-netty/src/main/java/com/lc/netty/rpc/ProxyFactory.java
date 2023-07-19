package com.lc.netty.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gujixian
 * @since 2023/7/18
 */
public class ProxyFactory {
    private static ConcurrentHashMap<String, Object> proxyMap = new ConcurrentHashMap<>();
    public static <T> T create(Class<?> clazz) {
        String key = clazz.getName();
        if (proxyMap.containsKey(key)) {
            return (T) proxyMap.get(key);
        }
        Object proxy = Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                new RpcInvocationHandler(clazz)
        );
        proxyMap.put(key, proxy);
        return (T) proxy;
    }

    private static class RpcInvocationHandler implements InvocationHandler {
        private final Class<?> clazz;
        public RpcInvocationHandler(Class<?> clazz) {
            this.clazz = clazz;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            // 构建 RpcRequest
            RpcProtocol rpcProtocol = new RpcProtocol();
            // 获取服务server（一个Channel），负载均衡
            Server server = new ServerFactory().getServer(clazz);
            // 发送请求，获取结果
            // 往 CallbackCenter 注册一个回调 CallbackCenter.addCallback(requestId, CompletableFuture)
            // --> future.complete("xx"); // client 接收Handler完成结果的填充
            // return CompletableFuture#get // 等待请求结果
            return server.doRequest(rpcProtocol);
        }
    }
}
