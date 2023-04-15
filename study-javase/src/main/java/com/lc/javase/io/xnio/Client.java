package com.lc.javase.io.xnio;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) {
        final String IP = "127.0.0.1";
        final int PORT = 9999;
        try {
            System.out.println("Client：正在连接服务器...");
            InetSocketAddress isa = new InetSocketAddress(IP, PORT);
            SocketChannel sc = SocketChannel.open(isa);
            new Thread(new ClientSender(sc), Math.random() + "").start();
            new Thread(new ClientReceiver(sc), Math.random() + "").start();
            System.out.println("Client：连接服务器成功!");
        } catch (Exception e) {
            System.out.println("Client：连接服务器失败!");
            e.printStackTrace();
        }
    }
}