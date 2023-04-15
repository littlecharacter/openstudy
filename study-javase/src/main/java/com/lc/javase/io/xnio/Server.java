package com.lc.javase.io.xnio;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Server：正在启动服务器...");
            new Thread(new ServerService()).start();
            System.out.println("Server：启动服务器成功!");
        } catch (Exception e) {
            System.out.println("Server：启动服务器失败!");
            e.printStackTrace();
        }
    }
}