package com.lc.javase.io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static volatile AtomicInteger ctl = new AtomicInteger(0);

    private static final String IP = "127.0.0.1";
    private static final int PORT = 9090;

    public static void main(String[] args) {
        try {
            System.out.println("client：正在连接服务器...");
            InetSocketAddress isa = new InetSocketAddress(IP, PORT);
            SocketChannel client = SocketChannel.open(isa);
            client.configureBlocking(false);
            System.out.println("client：连接服务器成功!");
            new Thread(new ClientSender(client)).start();
            new Thread(new ClientReceiver(client)).start();
        } catch (Exception e) {
            System.out.println("client：连接服务器失败!");
            e.printStackTrace();
        }
    }

    private static class ClientSender implements Runnable {
        private final SocketChannel client;

        public ClientSender(SocketChannel client) {
            this.client = client;
        }

        @Override
        public void run() {
            String content;
            final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            Scanner scanner = new Scanner(System.in);
            // 真的不用判断通道的状态吗？
            while (scanner.hasNextLine()) {
                try {
                    writeBuffer.clear();
                    content = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(content)) {
                        closeSocket();
                        ctl.set(1);
                        break;
                    }
                    try {
                        writeBuffer.put(StandardCharsets.UTF_8.encode(content));
                        writeBuffer.flip();
                        client.write(writeBuffer);
                    } catch (ClosedChannelException e) {
                        System.out.println("client sender：socket 已关闭!");
                        closeSocket();
                        break;
                    }
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

    private static class ClientReceiver implements Runnable {
        private final SocketChannel client;

        public ClientReceiver(SocketChannel client) {
            this.client = client;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
            while (true) {
                if (ctl.get() != 0) {
                    break;
                }
                try {
                    int read = client.read(buffer);  // >0  -1  0   //不会阻塞
                    if (read > 0) {
                        buffer.flip();
                        byte[] contentByte = new byte[buffer.limit()];
                        buffer.get(contentByte);

                        String content = new String(contentByte);
                        System.out.println(content + "\n");
                        buffer.clear();
                    }
                } catch (IOException e) {
                    System.out.println("client receiver：socket 已关闭!");
                    closeSocket();
                    break;
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