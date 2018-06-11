package com.lc.javase.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ServerHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {
    @Override
    public void completed(AsynchronousSocketChannel asc, Server server) {
        //当有下一个客户端接入的时候 直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
        server.getAssc().accept(server, this);
        read(asc);
    }

    @Override
    public void failed(Throwable exc, Server server) {
        exc.printStackTrace();
    }

    private void read(AsynchronousSocketChannel asc) {
        //读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        asc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer resultSize, ByteBuffer buffer) {
                //进行读取之后,重置标识位
                buffer.flip();
                //获得读取的字节数
                System.out.println("ServerHandler：收到客户端的数据长度为" + resultSize);
                //获取读取的数据
                String resultData = new String(buffer.array()).trim();
                System.out.println("ServerHandler：收到客户端的数据信息为" + resultData);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String response = "ServerHandler：收到了客户端发来的数据" + resultData;
                write(asc, response);
            }
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel asc, String response) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(response.getBytes());
            buffer.flip();
            asc.write(buffer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
