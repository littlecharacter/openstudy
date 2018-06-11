package com.lc.javase.io.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {
    //服务器通道
    private AsynchronousServerSocketChannel assc;

    private void init(int port) {
        System.out.println("Server：正在启动服务器...");
        try {
            //创建一个线程池
            ExecutorService executorService = Executors.newCachedThreadPool();
            //创建线程组
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            //创建服务器通道
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            //进行绑定
            assc.bind(new InetSocketAddress(port));
            System.out.println("Server：启动服务器成功!");
            //进行阻塞
            assc.accept(this, new ServerHandler());
            //一直阻塞 不让服务器停止
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            System.out.println("Server：启动服务器失败!");
            e.printStackTrace();
        }
    }

    public AsynchronousServerSocketChannel getAssc() {
        return assc;
    }

    public static void main(String[] args) {
        final int port = 9999;
        new Server().init(port);
    }
}
