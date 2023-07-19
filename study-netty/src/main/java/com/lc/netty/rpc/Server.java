package com.lc.netty.rpc;

import java.nio.channels.SocketChannel;

/**
 * @author gujixian
 * @since 2023/7/18
 */
public class Server {
    private SocketChannel channel;

    public SocketChannel getChannel() {
        return channel;
    }
    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public Object doRequest(RpcProtocol rpcProtocol) {
        // 往 CallbackCenter 注册一个回调 CallbackCenter.addCallback(requestId, CompletableFuture)
        // --> future.complete("xx"); // client 接收Handler完成结果的填充
        // return CompletableFuture#get // 等待请求结果
        return null;
    }
}
