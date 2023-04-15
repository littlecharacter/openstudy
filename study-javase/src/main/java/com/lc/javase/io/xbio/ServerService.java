package com.lc.javase.io.xbio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ServerService implements Runnable {
    private Socket socket;
    private List<Socket> nodes = new ArrayList<>();

    public ServerService(Socket sc, List<Socket> nodes) {
        this.socket = sc;
        this.nodes = nodes;
    }

    @Override
    public void run() {
        String server = "ServerService(" + Thread.currentThread().getName() + ")：";
        int client = socket.getPort();

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            String msg = server + "welcome " + client;
            writer.write(msg);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println(server + "发送消息到客户端(" + client + ")异常!");
        }

        String content;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            while (true) {
                content = reader.readLine();
                if (content == null) {
                    //检测连接是否还在
                    socket.sendUrgentData(0xFF);
                    continue;
                }
                System.out.println(content);
                for (Socket n : this.nodes) {
                    try {
                        if (n != this.socket) {
                            writer = new BufferedWriter(new OutputStreamWriter(n.getOutputStream(), "UTF-8"));
                            writer.write(content);
                            writer.newLine();
                            writer.flush();
                        }
                    } catch (IOException e) {
                        System.out.println(server + "群发消息到客户端(" + n.getPort() + ")异常!");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(server + "客户端(" + client + ")退出!");
            //e.printStackTrace();
            nodes.remove(socket);
            socket = null;
        }
    }
}
