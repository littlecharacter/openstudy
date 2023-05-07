package com.lc.netty.http;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * @author gujixian
 * @since 2023/5/8
 */
public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 添加HttpClientCodec，将字节流转换为HttpRequest和HttpResponse对象
        ch.pipeline().addLast(new HttpClientCodec());
        // 添加HttpObjectAggregator，将多个Http消息聚合为一个FullHttpRequest或FullHttpResponse
        ch.pipeline().addLast(new HttpObjectAggregator(65536));
        // 添加自定义的HttpClientHandler，处理Http响应并打印结果
        ch.pipeline().addLast(new HttpClientHandler());
    }
}