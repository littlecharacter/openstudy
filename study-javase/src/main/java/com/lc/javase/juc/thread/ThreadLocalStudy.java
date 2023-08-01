package com.lc.javase.juc.thread;

import java.sql.Connection;
import java.sql.DriverManager;

public class ThreadLocalStudy {
    public static void main(String[] args) {
        new Thread(() -> {
            TransactionManager.start();
            System.out.println("do something...");
            TransactionManager.commitAndClose();
        }).start();

        new Thread(() -> {
            TransactionManager.start();
            System.out.println("do something...");
            TransactionManager.rollbackAndClose();
        }).start();
    }

    static class TransactionManager {
        private static final String DB_URL = "jdbc:mysql://alix:3306/db_laboratory?user=lab-gogo&password=lab-gogo";
        // private static ThreadLocal<Connection> connectionHolder = ThreadLocal.withInitial(() -> {
        //     try {
        //         return DriverManager.getConnection(DB_URL);
        //     } catch (SQLException e) {
        //         e.printStackTrace();
        //         return null;
        //     }
        // });

        private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();


        public static Connection getConnection() {
            Connection connection = connectionHolder.get();
            if (connection == null) {
                try {
                    // Class.forName("com.mysql.jdbc.Driver");
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(DB_URL);
                    connectionHolder.set(connection);
                    return connection;
                } catch (Exception e) {
                    System.out.println("ThreadLocalStudy：获取数据库连接异常!");
                }
            }
            return connection;
        }

        public static void start() {
            try {
                Connection connection = getConnection();
                connection.setAutoCommit(false);
            } catch (Exception e) {
                System.out.println("TransactionManager：开启事务异常!");
            }
        }

        public static void commitAndClose() {
            try {
                Connection connection = getConnection();
                connection.commit();
                connection.close();
                connectionHolder.remove();
            } catch (Exception e) {
                System.out.println("TransactionManager：提交并关闭事务异常!");
            }
        }

        public static void rollbackAndClose() {
            try {
                Connection connection = getConnection();
                connection.rollback();
                connection.close();
                connectionHolder.remove();
            } catch (Exception e) {
                System.out.println("TransactionManager：回滚并关闭事务异常!");
            }
        }
    }
}
