package com.lc.javase.io.nio.server;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Arrays;
import java.util.List;

/**
 * 一个线程负责客户单连接，一堆线程负责通道读写
 */
public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Server：正在启动服务器...");
            List<Selector> selectors = Arrays.asList(Selector.open(), Selector.open());
            new Thread(new ListenService(selectors)).start();
            for (int i = 0; i < selectors.size(); i++) {
                Selector selector = selectors.get(i);
                new Thread(new HandleService(selector), Integer.toString(i)).start();
            }
            System.out.println("Server：启动服务器成功!");
        } catch (IOException e) {
            System.out.println("Server：启动服务器失败!");
            e.printStackTrace();
        }
    }
}