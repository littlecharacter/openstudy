package com.lc.javase.io.nio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class HandleService implements Runnable {
    private Selector selector;

    HandleService(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        final String server = "HandleService(" + Thread.currentThread().getName() + ")：";
        final Charset charset = Charset.forName("UTF-8");
        final ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        System.out.println("HandleService：等待客户端数据...");
        try {
            /**
             * selector.select()为阻塞式方法，如果没有就绪的注册通道就一直阻塞
             * selector.select(long timeout)设置阻塞时间
             * selector.selectNow()非阻塞方法，如果没有就绪的注册通道，就返回0
             */
            while (true) {
                if (selector.selectNow() == 0) {
                    Thread.yield();
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    // 从迭代器中移除
                    iterator.remove();
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
                        //分发到selector的其他通道 - 只是转发到注册到该selector上的通道，这种模式有多个selector，所以看现象别晕
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
