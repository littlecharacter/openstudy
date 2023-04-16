package com.lc.javase.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * 前提条件：启动服务端 -> nc -l 192.168.1.101 1080 和 nc -l 192.168.1.101 1080
 *
 * @author gujixian
 * @since 2023/4/10
 */
public class TcpReusePort {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket();
        // TCP 端口重用：需要放到 Linux 系统中使用，Window 系统不支持（可能需要设置一下系统参数，懒得整）
        client.setReuseAddress(true);
        client.bind(new InetSocketAddress(1234));
        // client.connect(new InetSocketAddress("192.168.1.101", 1080));
        client.connect(new InetSocketAddress("192.168.1.101", 1090));
        InputStream in = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        char[] buffer = new char[1024];
        while (true) {
            int resultFlag = reader.read(buffer);
            if (resultFlag != -1) {
                System.out.println(Arrays.toString(buffer));
            }
        }
    }
}
