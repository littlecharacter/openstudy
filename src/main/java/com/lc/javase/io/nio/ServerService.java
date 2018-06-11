package com.lc.javase.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ServerService implements Runnable {
    private static final int PORT = 9999;

    private Selector selector;
    private ServerSocketChannel ssc;

    ServerService() {
        try {
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false); //非阻塞方式
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        final String server = "ServerService(" + Thread.currentThread().getName() + ")：";
        final Charset charset = Charset.forName("UTF-8");
        final ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        try {
            /**
             * selector.select()为阻塞式方法，如果没有就绪的注册通道就一直阻塞
             * selector.select(long timeout)设置阻塞时间
             * selector.selectNow()非阻塞方法，如果没有就绪的注册通道，就返回0
             */
            while (selector.select() > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    // 从迭代器中移除
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        String client = sc.getRemoteAddress().toString().substring(1);
                        System.out.println(server + client + "连接成功!");
                        //懒，这里就不用Buffer了
                        sc.write(charset.encode(server + "欢迎" + client));
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel sc = (SocketChannel) selectionKey.channel();
                        StringBuilder sb = new StringBuilder();
                        readBuffer.clear();
                        try {
                            while (sc.read(readBuffer) > 0) {
                                readBuffer.flip();
                                sb.append(charset.decode(readBuffer));
                                readBuffer.clear();
                            }
                        } catch (IOException e) {
                            selectionKey.cancel();
                            continue;
                        }
                        System.out.println(sb.toString());
                        //分发到selector的其他通道
                        for (SelectionKey sk : selector.keys()) {
                            Channel channel = sk.channel();
                            if (channel instanceof SocketChannel && channel != sc) {
                                SocketChannel targetchannel = (SocketChannel) channel;
                                writeBuffer.clear();
                                writeBuffer.put(charset.encode(sb.toString()));
                                writeBuffer.flip();
                                targetchannel.write(writeBuffer);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(server + "异常关闭!");
            e.printStackTrace();
        }
    }
}
