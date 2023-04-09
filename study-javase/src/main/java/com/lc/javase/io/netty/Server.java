package com.lc.javase.io.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class Server {
    public static void main(String[] args) throws Exception {
        //1 第一个线程组 是用于接收Client端连接的
        /* 最佳实践是Boss配置1个Reactor线程，因为服务端Socket监听端口只能被一个Socket所占有，
         * 并且连接的建立是十分迅速的，因此Boss这里不会有性能问题
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //2 第二个线程组 是用于实际的业务处理操作的
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() + 1);
        //3 创建一个辅助类Bootstrap，就是对我们的Server进行一系列的配置
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 把俩个工作线程组加入进来
        bootstrap.group(bossGroup, workerGroup);
        // 我要指定使用NioServerSocketChannel这种类型的通道
        bootstrap.channel(NioServerSocketChannel.class);
        // TCP内核配置
        //bootstrap.option(ChannelOption.SO_BACKLOG, 1024); // 设置tcp缓冲区
        //bootstrap.option(ChannelOption.SO_SNDBUF, 32 * 1024); // 设置发送缓冲区
        //bootstrap.option(ChannelOption.SO_RCVBUF, 32 * 1024); // 设置接收缓冲区
        //bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // 保持连接，默认就是true
        // 一定要使用 childHandler 去绑定具体的 事件处理器
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                ChannelPipeline pipeline = sc.pipeline();
                // 定长传输解决拆包粘包问题
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                // 序列化反序列化
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                pipeline.addLast(new ServerHandler());
            }
        });
        // 绑定指定的端口 进行监听 非阻塞的 异步的（ChannelFuture）
        ChannelFuture f = bootstrap.bind(8765).sync();
        // 等待通道关闭通知
        f.channel().closeFuture().sync();
        System.out.println("通道关闭!");
        // 释放资源
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    private static class ServerHandler extends ChannelHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Request request = (Request) msg;
            System.out.println(JSON.toJSONString(request));
            Response response = new Response();
            response.setId(request.getId());
            response.setName(request.getName());
            response.setResponseMessage(request.getRequestMessage().toUpperCase());
            ctx.writeAndFlush(response);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
