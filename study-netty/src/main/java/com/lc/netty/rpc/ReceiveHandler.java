package com.lc.netty.rpc;

/**
 * @author gujixian
 * @since 2023/7/18
 */
public class ReceiveHandler {
    // 解析出RpcProtocol
    // CallbackCenter.runCallback(rpcProtocol)
    //     1，根据RpcProtocol.Head.requestId，获取CompletableFuture
    //     2，反序列化RpcProtocol.Response -> future.complete(obj);
}
