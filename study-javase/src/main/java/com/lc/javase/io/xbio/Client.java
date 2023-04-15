package com.lc.javase.io.xbio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 9999;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            System.out.println("client：正在连接服务器...");
            socket = new Socket(InetAddress.getByName(IP), PORT);
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

