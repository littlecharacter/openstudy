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
            /*
             * 往下追，最终调用的是 native 方法
             * select、poll：什么也不做
             * epoll：epoll_create -> fd3，指向内核开辟的一块红黑树空间
             */
            selector = Selector.open();  // select  poll  epoll
        } catch (IOException e) {
            System.out.println("server：多路服务器启动失败！");
            e.printStackTrace();
        }
    }

    private void startServer() {
        try {
            System.out.println("server：服务端启动...");
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            /*
             * 往下追，最终调用的是 native 方法
             * select：fd4，放在 JVM 里开辟的几个 fd 数组（每个关注事件的类型一个数组）里
             * poll：fd4，放在 JVM 里开辟的一个 fd 数组（fd 标记关注事件的类型）里
             * epoll：fd4，epoll_ctl（fd3, ADD, fd4, EPOLLIN）
             */
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
                /*
                 * 往下追，最终调用的是 native 方法
                 * select、poll：把 fd 数组传给内核，询问每个 fd 的状态
                 * epoll：epoll_wait(fd3, events, max_event, timeout)，获取内核中就绪的 fd
                 *        timeout：0-非阻塞调用，-1-阻塞调用，>0-限时阻塞调用，
                 *                 当 timeout 为 -1 时，Selector 提供 selector.wakeup() 唤醒 -> selector.select() 返回 0
                 */
                while (selector.select(500) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    // 不管何种多路复用器，得到的只是就绪 fd 的状态，用户进程还得一个个去处理 accept/read/write，这就是同步
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            this.handleAccept(key);
                            continue;
                        }
                        if (key.isReadable()) {
                            // 可能回阻塞 -> 下一步就是引入 IO Threads 专门处理
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


    public static void main(String[] args) {
        SingleReactorSingleThread server = new SingleReactorSingleThread();
        server.startServer();
        server.handleBusiness();
    }
}