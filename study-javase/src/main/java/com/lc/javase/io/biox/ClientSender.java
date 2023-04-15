package com.lc.javase.io.biox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class ClientSender implements Runnable {
    private Socket socket;

    public ClientSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String client = "ClientSender(" + Thread.currentThread().getName() + ")：";
        BufferedWriter writer = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String content;
            for (;;) {
                content = reader.readLine();
                if ("exit".equalsIgnoreCase(content)) {
                    socket.close();
                    break;
                }
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
                writer.write(client + content);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println(client + "断开服务端连接!");
            //e.printStackTrace();
        }
    }
}
