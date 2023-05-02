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
 * IO 多路复用线程模型：单 Reactor 单线程模型
 *
 * @author gujixian
 * @since 2023/5/1
 */
public class SingleReactorSingleThread {
    private final int port = 9090;
    private Selector selector;

    public SingleReactorSingleThread() {
        try {
            selector = Selector.open();  // select  poll  epoll
        } catch (IOException e) {
            System.out.println("server：多路服务器启动失败！");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SingleReactorSingleThread server = new SingleReactorSingleThread();
        server.startServer();
        server.handleBusiness();
    }

    private void startServer() {
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

    private void handleBusiness() {
        while (true) {
            try {
                Set<SelectionKey> allKeys = selector.keys();
                System.out.println("server：allKeys.size=" + allKeys.size());
                while (selector.select(500) > 0) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getClientName(Socket socket) {
        return socket.getLocalAddress().getHostName() + ":" + socket.getPort();
    }
}