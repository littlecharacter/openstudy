package com.lc.netty.v2;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.ReferenceCountUtil;

import java.util.Random;
import java.util.Scanner;

/**
 * @author gujixian
 * @since 2023-05-04
 */
public class Client {
    // public static void main(String[] args) throws Exception {
    //     // 只需要一个工作线程组即可
    //     EventLoopGroup workgroup = new NioEventLoopGroup();
    //     Bootstrap bootstrap = new Bootstrap();
    //     bootstrap.group(workgroup);
    //     bootstrap.channel(NioSocketChannel.class);
    //     bootstrap.handler(new ChannelInitializer<SocketChannel>() {
    //         @Override
    //         protected void initChannel(SocketChannel sc) throws Exception {
    //             ChannelPipeline pipeline = sc.pipeline();
    //             // 定长传输解决拆包粘包问题
    //             pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
    //             pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
    //             // 序列化反序列化
    //             pipeline.addLast(new ObjectEncoder());
    //             pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
    //             pipeline.addLast(new ClientHandler());
    //         }
    //     });
    //     ChannelFuture cf = bootstrap.connect("127.0.0.1", 8765).sync();
    //     Scanner scanner = new Scanner(System.in);
    //     while (scanner.hasNextLine()) {
    //         Request request = new Request();
    //         request.setId(String.valueOf(new Random().nextInt(Integer.MAX_VALUE)));
    //         request.setName("gujx");
    //         request.setRequestMessage(scanner.nextLine());
    //         cf.channel().writeAndFlush(request);
    //     }
    //     cf.channel().closeFuture().sync();
    //     workgroup.shutdownGracefully();
    // }
    //
    // private static class ClientHandler extends ChannelHandlerAdapter {
    //     // TODO 心跳检测用ScheduledExecutorService
    //     // @Override netty5 才有这个方法，这里另寻他法
    //     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    //         try {
    //             Response response = (Response) msg;
    //             System.out.println(JSON.toJSONString(response));
    //         } finally {
    //             ReferenceCountUtil.release(msg);
    //         }
    //     }
    //
    //     @Override
    //     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    //         cause.printStackTrace();
    //         ctx.close();
    //     }
    // }
}
