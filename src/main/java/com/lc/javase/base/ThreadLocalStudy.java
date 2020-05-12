package com.lc.javase.base;

import com.lc.javase.other.util.PropertyUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class ThreadLocalStudy {
    private static final String DB_URL = PropertyUtil.getProperty(PropertyUtil.URL)
            + "?" + PropertyUtil.NAME + "=" + PropertyUtil.getProperty(PropertyUtil.NAME)
            + "&" + PropertyUtil.PSWD + "=" + PropertyUtil.getProperty(PropertyUtil.PSWD);

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();


    public static Connection connection() {
        Connection connection = connectionHolder.get();
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL);
                connectionHolder.set(connection);
                return connection;
            } catch (Exception e) {
                System.out.println("ThreadLocalStudy：获取数据库连接异常!");
            }
        }
        return connection;
    }

    public static void commitAndClose(Connection connection) {
        try {
            if (connection != null) {
                connection.commit();
                connectionHolder.remove();
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("ThreadLocalStudy：提交并关闭连接异常!");
        }
    }

    public static void rollbackAndClose(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
                connectionHolder.remove();
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("ThreadLocalStudy：回滚并关闭连接异常!");
        }
    }
}
