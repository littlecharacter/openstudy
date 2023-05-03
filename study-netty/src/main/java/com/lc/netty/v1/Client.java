package com.lc.netty.v1;

import com.alibaba.fastjson.JSON;
import com.lc.netty.Request;
import com.lc.netty.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.Scanner;

/**
 * @author gujixian
 * @since 2023-05-03
 */
public class Client {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup(1);

        NioSocketChannel client = new NioSocketChannel();

        group.register(client);

        // 响应式编程
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new ReceiveHandler());

        ChannelFuture cf = client.connect(new InetSocketAddress("192.168.1.101", 9090)).sync();

        new Thread(new SendHandler(client)).start();

        cf.channel().closeFuture().sync();
        group.shutdownGracefully();
        System.out.println("game over!");
    }

    private static class SendHandler implements Runnable {
        private final NioSocketChannel client;

        public SendHandler(NioSocketChannel client) {
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
            super.channelRegistered(ctx);
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
