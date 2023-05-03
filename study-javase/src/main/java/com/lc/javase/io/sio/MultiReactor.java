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
 * IO 多路复用线程模型：多 Reactor
 *
 * @author gujixian
 * @since 2023/5/1
 */
public class MultiReactor {
    private final int port = 9090;
    private Selector bossSelector;
    private final Selector[] workSelectors = new Selector[2];

    public MultiReactor() {
        try {
            bossSelector = Selector.open();
            for (int i = 0; i < workSelectors.length; i++) {
                workSelectors[i] = Selector.open();
            }
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
            server.register(bossSelector, SelectionKey.OP_ACCEPT);
            System.out.println("server：服务端启动成功！");
        } catch (IOException e) {
            System.out.println("server：服务端启动失败！");
            e.printStackTrace();
        }
    }

    private void handle() {
        new Thread(new BossSelectorThread(bossSelector, workSelectors)).start();
        for (Selector workSelector : workSelectors) {
            new Thread(new WorkSelectorThread(workSelector)).start();
        }
    }

    private static class BossSelectorThread implements Runnable {
        private int xid = 0;
        private final Selector bossSelector;
        private final Selector[] workSelectors;
        private final int length;

        public BossSelectorThread(Selector bossSelector, Selector[] workSelectors) {
            this.bossSelector = bossSelector;
            this.workSelectors = workSelectors;
            this.length = workSelectors.length;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    while (bossSelector.select(500) > 0) {
                        Set<SelectionKey> selectionKeys = bossSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove();
                            if (key.isAcceptable()) {
                                this.handleAccept(key);
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
                client.register(this.choseSelector(), SelectionKey.OP_READ, buffer);
                System.out.println("server：客户端（" + this.getClientName(client.socket()) + "）连接成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Selector choseSelector() {
            if (xid >= length) {
                xid /= length;
            }
            return workSelectors[xid++];
        }

        private String getClientName(Socket socket) {
            return socket.getLocalAddress().getHostName() + ":" + socket.getPort();
        }
    }

    private static class WorkSelectorThread implements Runnable {
        private final Selector workSelector;

        public WorkSelectorThread(Selector workSelector) {

            this.workSelector = workSelector;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    while (workSelector.select(500) > 0) {
                        Set<SelectionKey> selectionKeys = workSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove();
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
                        byte[] contentByte = new byte[buffer.limit()];
                        buffer.get(contentByte);
                        String content = new String(contentByte);
                        System.out.println("server（" + Thread.currentThread().getName() + "）：客户端（" + getClientName(client.socket()) + "）：" + content);
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


    public static void main(String[] args) {
        MultiReactor server = new MultiReactor();
        server.start();
        server.handle();
    }
}
