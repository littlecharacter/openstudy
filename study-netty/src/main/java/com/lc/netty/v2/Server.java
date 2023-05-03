package com.lc.netty.v2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() + 1);
        ServerBootstrap bootstrap = new ServerBootstrap();
        // TCP内核配置
        //bootstrap.option(ChannelOption.SO_BACKLOG, 1024); // 设置 tcp listen 缓冲区
        //bootstrap.option(ChannelOption.SO_SNDBUF, 32 * 1024); // 设置发送缓冲区
        //bootstrap.option(ChannelOption.SO_RCVBUF, 32 * 1024); // 设置接收缓冲区
        //bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // 保持连接，默认就是true
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // .handler(...) // 不带 AcceptHandler
                // .childHandler(...) 自带 AcceptHandler
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast(new ReceiveHandler());
                    }
                });
        ChannelFuture cf = bootstrap.bind(new InetSocketAddress(9090)).sync();
        cf.channel().closeFuture().sync();
        // 释放资源
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("game over!");
    }

    private static class ReceiveHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("ReceiveHandler：registed！");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buffer = (ByteBuf) msg;
            // CharSequence content = buffer.readCharSequence(buffer.readableBytes(), CharsetUtil.UTF_8);
            CharSequence content = buffer.getCharSequence(0, buffer.readableBytes(), CharsetUtil.UTF_8);
            System.out.println(content);
            ctx.writeAndFlush(buffer);
        }
    }
}
