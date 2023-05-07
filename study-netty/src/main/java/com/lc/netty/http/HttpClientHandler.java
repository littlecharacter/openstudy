package com.lc.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.util.UUID;

/**
 * @author gujixian
 * @since 2023/5/8
 */
public class HttpClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 创建一个Http请求对象
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                HttpMethod.GET,
                "/test");
        // 设置请求头
        request.headers().set(HttpHeaderNames.HOST, "127.0.0.1");
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        request.headers().set("requestId", Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        // 通过添加 requestId，在 rpc 要使用 http 协议时，可以实现有状态的通信
        System.out.println("requestId:" + request.headers().get("requestId"));

        // 将请求对象写入到Channel
        ctx.writeAndFlush(request);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        // 可以通过 requestId，去回调对应的请求线程，以实现 rpc 的发送和接收异步
        System.out.println("requestId:" + response.headers().get("requestId"));
        // 获取响应内容
        ByteBuf content = response.content();
        byte[] bytes = new byte[content.readableBytes()];
        content.readBytes(bytes);
        String result = new String(bytes, "UTF-8");
        System.out.println("Response: " + result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
