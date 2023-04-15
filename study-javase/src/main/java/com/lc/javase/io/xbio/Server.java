package com.lc.javase.io.xbio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static int port = 9999;
    // 可接受请求队列的最大长度
    private static int backlog = 100;
    // 绑定到本机的IP地址
    private static final String bindAddr = "10.253.8.222";
    // socket字典列表
    private static List<Socket> nodes = new ArrayList<>(backlog);

    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port, backlog, InetAddress.getByName(bindAddr));
            System.out.println("server：启动成功!等待连接...");
            while (true) {
                if (ss == null) {
                    break;
                }
                // 发生阻塞,等待客户端连接
                try {
                    Socket sc = ss.accept();
                    nodes.add(sc);
                    InetAddress addr = sc.getLocalAddress();
                    System.out.println("server：" + addr.getHostName() + ":" + sc.getPort() + " connected!");
                    new Thread(new ServerService(sc, nodes), Math.random() +"").start();
                } catch (IOException e) {
                    System.out.println("server：有客户端连接失败!" + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("server：启动失败!");
            e.printStackTrace();
        } finally {
            try {
                if (ss != null) {
                    ss.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

