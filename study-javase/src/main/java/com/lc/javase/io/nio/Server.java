package com.lc.javase.io.nio;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * 客户端使用 nc 即可！
 *
 * @author gujixian
 * @since 2023/5/1
 */
public class Server {
    public static void main(String[] args) throws Exception {

        List<SocketChannel> clients = new LinkedList<>();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9090));
        ssc.configureBlocking(false); //重点  OS  NONBLOCKING!!!

        /**
         * StandardSocketOptions.TCP_NODELAY
         * StandardSocketOptions.SO_KEEPALIVE
         * StandardSocketOptions.SO_LINGER
         * StandardSocketOptions.SO_RCVBUF
         * StandardSocketOptions.SO_SNDBUF
         * StandardSocketOptions.SO_REUSEADDR
         */
        // ssc.setOption(StandardSocketOptions.TCP_NODELAY, true);

        while (true) {
            Thread.sleep(1000);
            SocketChannel client = ssc.accept(); //不会阻塞 没有链接进来就返回null
            if (client == null) {
                System.out.println("server：监听客户端连接...");
            } else {
                client.configureBlocking(false);
                System.out.println("server：客户端（" + client.socket().getLocalAddress().getHostName() + ":" + client.socket().getPort() + "）连接成功！");
                clients.add(client);
            }

            // ByteBuffer buffer = ByteBuffer.allocate(4096); JVM 堆里
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096); // JVM 堆外

            for (SocketChannel c : clients) {   //串行化！！！！  多线程！！
                int num = c.read(buffer);  // >0  -1  0   //不会阻塞
                if (num > 0) {
                    buffer.flip();
                    byte[] contentByte = new byte[buffer.limit()];
                    buffer.get(contentByte);

                    String content = new String(contentByte);
                    System.out.println("server：客户端（" + c.socket().getLocalAddress().getHostName() + ":" + c.socket().getPort() + "）：" + content);
                    buffer.clear();
                }
            }
        }
    }
}
