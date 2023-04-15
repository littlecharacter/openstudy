package com.lc.javase.io.xnio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ClientSender implements Runnable {
    private SocketChannel sc;

    public ClientSender(SocketChannel sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        final String client = "ClientSender(" + Thread.currentThread().getName() + ")：";
        final Charset charset = Charset.forName("UTF-8");
        final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        try {
            Scanner scanner = new Scanner(System.in);
            // 真的不用判断通道的状态吗？
            while (scanner.hasNextLine()) {
                writeBuffer.clear();
                writeBuffer.put(charset.encode(client + scanner.nextLine()));
                writeBuffer.flip();
                sc.write(writeBuffer);
            }
        } catch (IOException e) {
            System.out.println(client + "异常关闭!");
            e.printStackTrace();
        }
    }
}
