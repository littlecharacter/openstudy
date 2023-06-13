package com.lc.javase.io.nio;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 客户端使用 nc 即可！
 *
 * @author gujixian
 * @since 2023/5/1
 */
public class Server {
    private static final Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) {
        ServerSocketChannel ssc;
        try {
            System.out.println("server：服务端启动...");
            ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(9090));
            ssc.configureBlocking(false); //重点  OS  NONBLOCKING!!!
            System.out.println("server：服务端启动成功！");
        } catch (IOException e) {
            System.out.println("server：服务端启动失败！");
            e.printStackTrace();
            return;
        }

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
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                SocketChannel client = ssc.accept(); // 不会阻塞 没有链接进来就返回null
                if (client == null) {
                    System.out.println("server：监听客户端连接...");
                } else {
                    client.configureBlocking(false);
                    System.out.println("server：客户端（" + getClientName(client.socket()) + "）连接成功！");
                    clientMap.put(getClientName(client.socket()), client);
                }

                // ByteBuffer buffer = ByteBuffer.allocate(4096); JVM 堆里
                ByteBuffer buffer = ByteBuffer.allocateDirect(4096);// JVM 堆外

                System.out.println(JSON.toJSONString(clientMap));
                for (Iterator<Map.Entry<String, SocketChannel>> iterator = clientMap.entrySet().iterator(); iterator.hasNext();){
                    Map.Entry<String, SocketChannel> entry = iterator.next();
                    try {
                        int read = entry.getValue().read(buffer);  // >0  -1  0   //不会阻塞
                        if (read == -1) {
                            System.out.println("server：客户端（" + getClientName(entry.getValue().socket()) + " closed!");
                            closeSocket(entry.getValue().socket());
                            iterator.remove();
                            continue;
                        }
                        if (read > 0) {
                            buffer.flip();
                            byte[] contentByte = new byte[buffer.limit()];
                            buffer.get(contentByte);

                            String content = new String(contentByte);
                            System.out.println("server：客户端（" + getClientName(entry.getValue().socket()) + "）：" + content);
                            buffer.clear();

                            content = content.toUpperCase();
                            buffer.put(StandardCharsets.UTF_8.encode(content));
                            buffer.flip();
                            entry.getValue().write(buffer);
                            buffer.clear();
                        }
                    } catch (IOException e) {
                        System.out.println("server：客户端（" + getClientName(entry.getValue().socket()) + " closed!");
                        closeSocket(entry.getValue().socket());
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getClientName(Socket socket) {
        return socket.getLocalAddress().getHostName() + ":" + socket.getPort();
    }

    private static void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
