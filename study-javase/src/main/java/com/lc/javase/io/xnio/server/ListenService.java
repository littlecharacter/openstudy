package com.lc.javase.io.xnio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;

public class ListenService implements Runnable {
    private static final int PORT = 8888;

    private ServerSocketChannel ssc;
    private List<Selector> selectors;

    ListenService(List<Selector> selectors) {
        this.selectors = selectors;
        try {
            ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        final Charset charset = Charset.forName("UTF-8");
        System.out.println("ListenService：等待客户端连接...");
        for (; ; ) {
            try {
                if (ssc == null) {
                    break;
                }
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                Selector selector = selectors.get(sc.hashCode() % selectors.size());
                //sc.register()和selector.select()存在竞争
                sc.register(selector, SelectionKey.OP_READ);
                String client = sc.getRemoteAddress().toString().substring(1);
                System.out.println("ListenService：" + client + "连接成功!");
                //懒，这里就不用Buffer了
                sc.write(charset.encode("ListenService：欢迎" + client));
            } catch (IOException e) {
                System.out.println("ListenService：有客户端连接失败!");
                e.printStackTrace();
            }
        }
    }
}
