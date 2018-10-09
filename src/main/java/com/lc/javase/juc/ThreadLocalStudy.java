package com.lc.javase.juc;

import com.lc.javase.util.PropertyUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class ThreadLocalStudy {
    private static final String DB_URL = PropertyUtil.getProperty(PropertyUtil.URL)
            + "?" + PropertyUtil.NAME + "=" + PropertyUtil.getProperty(PropertyUtil.NAME)
            + "&" + PropertyUtil.PSWD + "=" + PropertyUtil.getProperty(PropertyUtil.PSWD);
    private static ThreadLocal<Connection> connectionHolder = ThreadLocal.withInitial(() -> {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            System.out.println("ThreadLocalStudy:获取数据库连接异常!");
            return null;
        }
    });

    public static Connection getConnection() {
        if (connectionHolder == null) {
            return null;
        }
        return connectionHolder.get();
    }
}
