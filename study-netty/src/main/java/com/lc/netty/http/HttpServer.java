package com.lc.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author gujixian
 * @since 2023/5/8
 */
public class HttpServer {

    private int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // 创建两个EventLoopGroup，一个用于接受客户端连接，一个用于处理IO事件
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建ServerBootstrap对象，用于配置服务端参数
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 指定使用NioServerSocketChannel作为服务端通道
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 设置子通道的处理器
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 添加自定义的处理器
                            ch.pipeline().addLast(new HttpServerInitializer());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置服务端接受连接的队列长度
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // 设置子通道保持连接

            // 绑定端口并启动服务端
            ChannelFuture f = b.bind(port).sync();

            System.out.println("Http Server started at port " + port);

            // 等待服务端关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅地关闭EventLoopGroup
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpServer(8080).start();
    }
}
