package com.lc.javase.io.sio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * IO 多路复用线程模型：多 Reactor（主从模型）
 *
 * EventLoop - Selector
 * EventLoopGroup - 负载均衡
 * Bootstrap - 组织者
 * 
 * @author gujixian
 * @since 2023/5/1
 */
public class MultiReactorV2 {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new EventLoopGroup(3, "BOSS");
        EventLoopGroup workGroup = new EventLoopGroup(5, "WORK");

        // TimeUnit.SECONDS.sleep(5);

        Bootstrap bootstrap = new Bootstrap().group(bossGroup, workGroup);

        bootstrap.bind(9090);
        bootstrap.bind(9191);
        bootstrap.bind(9292);
        bootstrap.bind(9393);

        System.out.println(System.in.read());
    }


    private static class EventLoop implements Runnable {
        private final String name;
        private Selector selector;
        private BlockingQueue<Channel> taskQueue = new LinkedBlockingQueue<>();
        private EventLoopGroup bossGroup;
        private EventLoopGroup workGroup;

        public EventLoop(String name) {
            this.name = name;
            try {
                selector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void setGroup(EventLoopGroup bossGroup, EventLoopGroup workGroup) {
            this.bossGroup = bossGroup;
            this.workGroup = workGroup;
        }

        @Override
        public void run() {
            System.out.println(name + "：启动成功！");
            while (true) {
                try {
                    while (selector.select() > 0) {
                        // Set<SelectionKey> allKeys = selector.keys();
                        // System.out.println(name + "：监听/负责 channel 的数量为" + allKeys.size());
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
                    while (!taskQueue.isEmpty()) {
                        Channel channel = taskQueue.poll();
                        if (channel instanceof ServerSocketChannel) {
                            ServerSocketChannel server = (ServerSocketChannel) channel;
                            server.register(selector, SelectionKey.OP_ACCEPT);
                            System.out.println(name + "：监听" + server.socket().getLocalPort() + "端口...");
                        } else if (channel instanceof SocketChannel) {
                            SocketChannel client = (SocketChannel) channel;
                            ByteBuffer buffer = ByteBuffer.allocate(8192);
                            client.register(selector, SelectionKey.OP_READ, buffer);
                            System.out.println(name + "：负责 client（" + this.getClientName(client.socket()) + "）");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleAccept(SelectionKey key) {
            try {
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                SocketChannel client = server.accept();
                client.configureBlocking(false);
                System.out.println(name + "：client（" + this.getClientName(client.socket()) + "）连接成功！");

                EventLoop eventLoop = workGroup.chose();
                eventLoop.addTask(client);
                eventLoop.wakeUp();
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
                        byte[] contentByte = new byte[buffer.limit()];
                        buffer.get(contentByte);
                        String content = new String(contentByte);
                        System.out.println(name + "：client（" + getClientName(client.socket()) + "）：" + content);
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

        public void addTask(Channel channel) {
            taskQueue.offer(channel);
        }

        public void wakeUp() {
            this.selector.wakeup();
        }

        private String getClientName(Socket socket) {
            return socket.getLocalAddress().getHostName() + ":" + socket.getPort();
        }
    }

    private static class EventLoopGroup {
        AtomicInteger cid = new AtomicInteger(0);
        private final int threadNum;
        private EventLoop[] eventLoops;

        public EventLoopGroup(int threadNum, String groupName) {
            this.threadNum = threadNum;
            eventLoops = new EventLoop[threadNum];
            for (int i = 0; i < eventLoops.length; i++) {
                EventLoop eventLoop = new EventLoop(groupName + "-" + i);
                eventLoops[i] = eventLoop;
                new Thread(eventLoop).start();
            }
        }

        public void setGroup(EventLoopGroup bossGroup, EventLoopGroup workGroup) {
            for (EventLoop eventLoop : eventLoops) {
                eventLoop.setGroup(bossGroup, workGroup);
            }
        }

        public EventLoop chose() {
            if (cid.get() >= threadNum) {
                cid.set(cid.get() % threadNum);
            }
            return eventLoops[cid.getAndIncrement()];
        }

    }

    private static class Bootstrap {
        private EventLoopGroup bossGroup;
        private EventLoopGroup workGroup;

        public Bootstrap group(EventLoopGroup bossGroup, EventLoopGroup workGroup) {
            this.bossGroup = bossGroup;
            this.workGroup = workGroup;
            this.bossGroup.setGroup(bossGroup, workGroup);
            this.workGroup.setGroup(bossGroup, workGroup);
            return this;
        }

        public void bind(int port) {
            try {
                ServerSocketChannel ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);
                ssc.bind(new InetSocketAddress(port));
                EventLoop eventLoop = bossGroup.chose();
                eventLoop.addTask(ssc);
                eventLoop.wakeUp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
