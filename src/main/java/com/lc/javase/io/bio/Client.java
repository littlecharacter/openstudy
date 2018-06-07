package com.lc.javase.io.bio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final int port = 9999;
    private static final String bindAddr = "10.253.8.222";

    public static void main(String[] args) {
        Socket socket = null;
        try {
            System.out.println("client：正在连接服务器...");
            socket = new Socket(InetAddress.getByName(bindAddr), port);
            new Thread(new ClientSender(socket), Math.random() +"").start();
            new Thread(new ClientReceiver(socket), Math.random() +"").start();
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
}

