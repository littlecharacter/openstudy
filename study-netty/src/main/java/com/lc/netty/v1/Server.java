package com.lc.netty.v1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup(3);

        NioServerSocketChannel server = new NioServerSocketChannel();

        group.register(server);

        // 响应式编程
        ChannelPipeline pipeline = server.pipeline();
        pipeline.addLast(new AcceptHandler(group, new ChannelInitializer() {
            @Override
            void initChannel(SocketChannel sc) {
                ChannelPipeline cp = sc.pipeline();
                cp.addLast(new ReceiveHandler());
                System.out.println("ReceiveHandler：registed！");
            }
        }));

        ChannelFuture cf = server.bind(new InetSocketAddress(9090)).sync();

        cf.channel().closeFuture().sync();
        group.shutdownGracefully();

        System.out.println("game over!");
    }

    private static class AcceptHandler extends ChannelInboundHandlerAdapter {
        private final EventLoopGroup group;
        private final ChannelInitializer channelInitializer;

        public AcceptHandler(EventLoopGroup group, ChannelInitializer channelInitializer) {
            this.group = group;
            this.channelInitializer = channelInitializer;
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("AcceptHandler：registed！");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            SocketChannel client = (SocketChannel) msg;
            ChannelPipeline pipeline = client.pipeline();
            pipeline.addLast(channelInitializer);
            group.register(client);
        }
    }

    /**
     * 嫁衣：过河拆桥、卸磨杀驴 -> 为了用户可以自定义业务处理 Handler（比如 ReceiveHandler），而做的嫁衣
     */
    @ChannelHandler.Sharable
    private static abstract class ChannelInitializer extends ChannelInboundHandlerAdapter {

        abstract void initChannel(SocketChannel sc);

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("ChannelInitializer：registed！");
            SocketChannel client = (SocketChannel) ctx.channel();
            this.initChannel(client);
            // 过河拆桥、卸磨杀驴
            ChannelPipeline pipeline = client.pipeline();
            pipeline.remove(this);
            System.out.println("ChannelInitializer：removed！");
        }
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
