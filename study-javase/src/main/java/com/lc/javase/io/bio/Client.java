package com.lc.javase.io.bio;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author gujixian
 * @since 2023/4/15
 */
public class Client {
    private static final String IP = "192.168.1.101";
    private static final int PORT = 10250;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            System.out.println("client：正在连接服务器...");
            socket = new Socket(InetAddress.getByName(IP), PORT);
            System.out.println("client：连接服务器成功!");
            new Thread(new ClientSender(socket)).start();
            new Thread(new ClientReceiver(socket)).start();
        } catch (Exception e) {
            System.out.println("client：连接服务器失败!");
            e.printStackTrace();
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    writer.write(content);
                    writer.newLine();
                    writer.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientReceiver implements Runnable {
        private BufferedReader reader;

        public ClientReceiver(Socket socket) {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            String content;
            while (true) {
                try {
                    content = reader.readLine();
                    System.out.println(content + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
