package com.lc.netty.v2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author gujixian
 * @since 2023-05-04
 */
public class Client {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workgroup = new NioEventLoopGroup(1);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workgroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ReceiveHandler());
                    }
                });
        ChannelFuture cf = bootstrap.connect(new InetSocketAddress("192.168.1.101", 9090)).sync();

        new Thread(new SendHandler(cf.channel())).start();

        cf.channel().closeFuture().sync();
        workgroup.shutdownGracefully();
    }

    private static class SendHandler implements Runnable {
        private final Channel client;

        public SendHandler(Channel client) {
            this.client = client;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String content = scanner.nextLine();
                ByteBuf buf = Unpooled.copiedBuffer(content.getBytes());
                client.writeAndFlush(buf);
            }
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
