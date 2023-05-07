package com.lc.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author gujixian
 * @since 2023/5/8
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 添加HttpServerCodec，将字节流转换为HttpRequest和HttpResponse对象
        ch.pipeline().addLast(new HttpServerCodec());
        // 添加HttpObjectAggregator，将多个Http消息聚合为一个FullHttpRequest或FullHttpResponse
        ch.pipeline().addLast(new HttpObjectAggregator(65536));
        // 添加自定义的HttpServerHandler，处理Http请求并返回响应
        ch.pipeline().addLast(new HttpServerHandler());
    }
}