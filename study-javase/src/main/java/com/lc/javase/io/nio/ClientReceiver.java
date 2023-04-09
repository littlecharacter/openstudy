package com.lc.javase.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ClientReceiver implements Runnable {
    Selector selector;

    public ClientReceiver(SocketChannel sc) {
        try {
            selector = Selector.open();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        final String client = "ClientReceiver(" + Thread.currentThread().getName() + ")：";
        final Charset charset = Charset.forName("UTF-8");
        final ByteBuffer readBuffer = ByteBuffer.allocate(1024);
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
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(client + "异常关闭!");
            e.printStackTrace();
        }
    }
}
