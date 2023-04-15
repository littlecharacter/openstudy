package com.lc.javase.io.biox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientReceiver implements Runnable {
    private Socket socket;

    public ClientReceiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String client = "ClientReceiver(" + Thread.currentThread().getName() + ")：";
        BufferedReader reader = null;
        String content;
        try {
            while (true) {
                if (socket == null) {
                    break;
                }
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                content = reader.readLine();
                if (content == null) {
                    //检测连接是否还在
                    socket.sendUrgentData(0xFF);
                    continue;
                }
                System.out.println(content + "\n");
            }
        } catch (Exception e) {
            System.out.println(client + "断开服务端连接!");
            //e.printStackTrace();
        }
    }
}
