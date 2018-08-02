package com.lc.javase.thread;

import org.junit.Test;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class ThreadLocalStudyTest {
    @Test
    public void testThreadLoacl() throws Exception {
        new Thread(() -> {
            //两个线程中的connecton指向的不是同一个对象-这就是ThreadLocal的作用
            Connection connection = ThreadLocalStudy.getConnection();
            try {
                assert connection != null;
                connection.setClientInfo("connection-1", "第一个数据库连接");
                TimeUnit.MILLISECONDS.sleep(1000L);
                System.out.println(Thread.currentThread().getName() + ":" + connection.getClientInfo());
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + ":异常!");
            }
        }).start();

        new Thread(() -> {
            //两个线程中的connecton指向的不是同一个对象-这就是ThreadLocal的作用
            Connection connection = ThreadLocalStudy.getConnection();
            try {
                assert connection != null;
                connection.setClientInfo("connection-2", "第二个数据库连接");
                TimeUnit.MILLISECONDS.sleep(1000L);
                System.out.println(Thread.currentThread().getName() + ":" + connection.getClientInfo());
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + ":异常!");
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(5000L);
    }
}