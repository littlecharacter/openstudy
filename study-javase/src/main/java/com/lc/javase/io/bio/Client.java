package com.lc.javase.io.bio;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gujixian
 * @since 2023/4/15
 */
public class Client {
    private static volatile AtomicInteger ctl = new AtomicInteger(0);

    private static final String IP = "127.0.0.1";
    private static final int PORT = 9999;

    public static void main(String[] args) {
        try {
            System.out.println("client：正在连接服务器...");
            Socket socket = new Socket(InetAddress.getByName(IP), PORT);
            System.out.println("client：连接服务器成功!");
            new Thread(new ClientSender(socket)).start();
            // 如果有两个线程读取同一个 Socket，那么这两个线程会交替读取 Socket <- 这里模拟的是，每次读的时候都会阻塞，进程挂起在 Socket 的等待队列
            new Thread(new ClientReceiver(socket)).start();
            new Thread(new ClientReceiver(socket)).start();
        } catch (Exception e) {
            System.out.println("client：连接服务器失败!");
            e.printStackTrace();
        }
    }

    private static class ClientSender implements Runnable {
        private final Socket socket;
        private BufferedReader reader;
        private BufferedWriter writer;

        public ClientSender(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(System.in));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String content;
            for (;;) {
                try {
                    content = reader.readLine();
                    if ("exit".equalsIgnoreCase(content)) {
                        closeSocket();
                        ctl.set(1);
                        break;
                    }
                    try {
                        writer.write(content);
                        writer.newLine();
                        writer.flush();
                    } catch (SocketException e) {
                        System.out.println("client sender：socket 已关闭!");
                        this.closeSocket();
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void closeSocket() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ClientReceiver implements Runnable {
        private final Socket socket;
        private BufferedReader reader;

        public ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            String content;
            while (true) {
                if (ctl.get() != 0) {
                    break;
                }
                try {
                    try {
                        content = reader.readLine();
                    } catch (SocketException e) {
                        System.out.println("client receiver：socket 已关闭!");
                        closeSocket();
                        break;
                    }
                    if (Objects.isNull(content)) {
                        continue;
                    }
                    System.out.println(content + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void closeSocket() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
