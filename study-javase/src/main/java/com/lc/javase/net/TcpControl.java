package com.lc.javase.net;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TCP 连接和传输控制
 * 1、服务启动之后，开数量大于 BACK_LOG+1 的客户端去连接，期间卡在控制点，netstat 和 ss 观察 Send-Q、Recv-Q、State、PID
 * 2、客户端发送数据，多发点，期间依旧卡在控制点，netstate 和 ss 观察 Send-Q、Recv-Q、State、PID
 * 3、tcpdum -nn -i eth0 port 9090 观察客户端数据发送时"接收窗口K"的变化
 *
 * @author gujixian
 * @since 2023/4/16
 */
public class TcpControl {
    public static volatile AtomicInteger ctl = new AtomicInteger(0);

    // server socket (listen) property:
    private static final int RECEIVE_BUFFER = 10;
    private static final int SO_TIMEOUT = 0;
    private static final boolean REUSE_ADDR = false;
    private static final int BACK_LOG = 2;
    // client socket (listen) property on server endpoint:
    private static final boolean CLI_KEEPALIVE = true;
    // 是否在发送内容前，先吧内容的第一个字节发出去 -> 提前确认网络通畅，一般没必要开
    private static final boolean CLI_OOB = false;
    private static final int CLI_REC_BUF = 20;
    private static final boolean CLI_REUSE_ADDR = false;
    private static final int CLI_SEND_BUF = 20;
    // 断开连接的速度
    private static final boolean CLI_LINGER = true;
    private static final int CLI_LINGER_N = 0;
    // 是否实时发送，一般关闭
    private static final boolean CLI_NO_DELAY = false;
    private static final int CLI_TIMEOUT = 0;

    // StandardSocketoptions.TCP_NODELAY
    // StandardSocketOptions.SO_KEEPALIVE
    // StandardSocketOptions.SO_LINGER
    // StandardSocketOptions.SO_RCVBUF
    // StandardSocketOptions.SO_SNDBUF
    // StandardSocketOptions.SO_REUSEADDR

    public static void main(String[] args) {
        String role = args[1];
        if (Objects.equals("client", role)) {
            startClient();
        } else if (Objects.equals("server", role)) {
            startServer();
        }
    }

    private static void startClient() {
        Socket socket = null;
        try {
            System.out.println("client：正在连接服务器...");
            socket = new Socket("192.168.1.101", 9090);
            System.out.println("client：连接服务器成功!");

            socket.setTcpNoDelay(CLI_NO_DELAY);
            socket.setSendBufferSize(CLI_SEND_BUF);
            socket.setKeepAlive(CLI_KEEPALIVE);


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
                        }
                        ctl.set(1);
                        break;
                    }
                    try {
                        writer.write(content);
                        writer.newLine();
                        writer.flush();
                    } catch (SocketException e) {
                        if (Objects.nonNull(socket)) {
                            System.out.println("client sender：socket closed!");
                            socket.close();
                        }
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientReceiver implements Runnable {
        private final Socket socket;
        private BufferedReader reader;

        public ClientReceiver(Socket socket) {
            this.socket = socket;
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
                if (ctl.get() != 0) {
                    break;
                }
                try {
                    try {
                        content = reader.readLine();
                    } catch (SocketException e) {
                        if (Objects.nonNull(socket)) {
                            System.out.println("client receiver：socket closed!");
                            socket.close();
                        }
                        break;
                    }
                    if (Objects.isNull(content)) {
                        continue;
                    }
                    System.out.println(content + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void startServer() {
        ServerSocket ss = null;
        try {
            // ss = new ServerSocket(port, backlog, InetAddress.getByName(bindAddr));
            ss = new ServerSocket();
            ss.bind(new InetSocketAddress(9090), BACK_LOG);

            ss.setReceiveBufferSize(RECEIVE_BUFFER);
            // ss.setReuseAddress(REUSE_ADDR);
            ss.setSoTimeout(SO_TIMEOUT);

            System.out.println("server：启动成功!等待连接...");
            while (true) {
                try {
                    // 控制点
                    System.in.read();
                    // 发生阻塞,等待客户端连接
                    Socket sc = ss.accept();
                    InetAddress addr = sc.getLocalAddress();
                    System.out.println("server：" + addr.getHostName() + ":" + sc.getPort() + " connected!");

                    sc.setKeepAlive(CLI_KEEPALIVE);
                    sc.setOOBInline(CLI_OOB);
                    sc.setTcpNoDelay(CLI_NO_DELAY);
                    sc.setReceiveBufferSize(CLI_REC_BUF);
                    sc.setSendBufferSize(CLI_SEND_BUF);
                    sc.setSoLinger(CLI_LINGER, CLI_LINGER_N);
                    sc.setSoTimeout(CLI_TIMEOUT);

                    // 为新的连接分配一个线程处理读写任务
                    new Thread(new ServerService(sc)).start();
                } catch (IOException e) {
                    System.out.println("server：有客户端连接失败!" + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("server：启动失败!");
            e.printStackTrace();
        } finally {
            try {
                if (ss != null) {
                    ss.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ServerService implements Runnable {
        private final Socket sc;
        private BufferedReader reader;
        private BufferedWriter writer;

        public ServerService(Socket sc) {
            this.sc = sc;
            try {
                reader = new BufferedReader(new InputStreamReader(sc.getInputStream(), StandardCharsets.UTF_8));
                writer = new BufferedWriter(new OutputStreamWriter(sc.getOutputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String content;
            while (true) {
                try {
                    try {
                        content = reader.readLine();
                    } catch (SocketException e) {
                        System.out.println("server：" + sc.getLocalAddress().getHostName() + ":" + sc.getPort() + " closed!");
                        sc.close();
                        break;
                    }
                    if (Objects.isNull(content)) {
                        continue;
                    }
                    System.out.println(sc.getLocalAddress().getHostName() + ":" + sc.getPort() + "：" + content);
                    writer.write(content.toUpperCase());
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
