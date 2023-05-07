package com.lc.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author gujixian
 * @since 2023/5/8
 */
public class HttpClient {

    private String host;

    private int port;

    public HttpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        // 创建一个EventLoopGroup，用于处理IO事件
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建Bootstrap对象，用于配置客户端参数
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class) // 指定使用NioSocketChannel作为客户端通道
                    .handler(new ChannelInitializer<SocketChannel>() { // 设置通道的处理器
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 添加自定义的处理器
                            ch.pipeline().addLast(new HttpClientInitializer());
                        }
                    });

            // 连接到服务端
            ChannelFuture f = b.connect(host, port).sync();

            System.out.println("Http Client started at " + host + ":" + port);

            // 等待客户端关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅地关闭EventLoopGroup
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpClient("127.0.0.1", 8080).start();
    }
}
