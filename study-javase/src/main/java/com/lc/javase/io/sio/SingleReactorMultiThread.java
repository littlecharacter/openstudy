package com.lc.javase.io.sio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * IO 多路复用线程模型：单 Reactor 多线程模型 - V1
 *
 * @author gujixian
 * @since 2023/5/1
 */
public class SingleReactorMultiThread {
    private final int port = 9090;
    private Selector selector;

    public SingleReactorMultiThread() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            System.out.println("server：多路服务器启动失败！");
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            System.out.println("server：服务端启动...");
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server：服务端启动成功！");
        } catch (IOException e) {
            System.out.println("server：服务端启动失败！");
            e.printStackTrace();
        }
    }

    private void handle() {
        while (true) {
            try {
                Set<SelectionKey> allKeys = selector.keys();
                System.out.println("server：allKeys.size=" + allKeys.size());
                while (selector.select(1) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            this.handleAccept(key);
                            continue;
                        }
                        if (key.isReadable()) {
                            // 问题：重复 epoll_ctl(.., del, ...) -> 如果不 cancel，那么读写的过程中，这里回被多次执行到！
                            key.cancel();
                            this.handleRead(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAccept(SelectionKey key) {
        try {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("server：客户端（" + this.getClientName(client.socket()) + "）连接成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRead(SelectionKey key) {
        new Thread(() -> {
            System.out.println("server：handleRead...");
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            int read;
            try {
                while (true) {
                    read = client.read(buffer);
                    if (read > 0) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            client.write(buffer);
                        }
                        buffer.clear();
                    } else if (read == 0) {
                        break;
                    } else {
                        client.close();
                        break;
                    }
                }
                // 问题：重复 epoll_ctl(.., add, ..)
                client.register(selector, SelectionKey.OP_READ, buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String getClientName(Socket socket) {
        return socket.getLocalAddress().getHostName() + ":" + socket.getPort();
    }


    public static void main(String[] args) {
        SingleReactorMultiThread server = new SingleReactorMultiThread();
        server.start();
        server.handle();
    }
}
