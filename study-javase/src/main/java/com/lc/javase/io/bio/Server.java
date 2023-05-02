package com.lc.javase.io.bio;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author gujixian
 * @since 2023/4/15
 */
public class Server {
    private static int port = 9999;
    // 可接受请求队列的最大长度
    private static int backlog = 100;
    // 绑定到本机的IP地址
    // private static final String bindAddr = "10.253.8.222";

    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            // ss = new ServerSocket(port, backlog, InetAddress.getByName(bindAddr));
            ss = new ServerSocket(port, backlog);
            System.out.println("server：启动成功!等待连接...");
            while (true) {
                // 发生阻塞,等待客户端连接
                try {
                    Socket client = ss.accept();
                    InetAddress addr = client.getLocalAddress();
                    System.out.println("server：" + addr.getHostName() + ":" + client.getPort() + " connected!");
                    // 为新的连接分配一个线程处理读写任务
                    new Thread(new ServerService(client)).start();
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

    private static class ServerService implements Runnable {
        private final Socket client;
        private BufferedReader reader;
        private BufferedWriter writer;

        public ServerService(Socket client) {
            this.client = client;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
                writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String content;
            while (true) {
                try {
                    try {
                        content = reader.readLine();
                    } catch (SocketException e) {
                        System.out.println("server：" + client.getLocalAddress().getHostName() + ":" + client.getPort() + " closed!");
                        closeSocket();
                        break;
                    }
                    if (Objects.isNull(content)) {
                        continue;
                    }
                    System.out.println(client.getLocalAddress().getHostName() + ":" + client.getPort() + "：" + content);
                    writer.write(content.toUpperCase());
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void closeSocket() {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
