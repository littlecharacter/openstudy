package com.lc.mybatis;

import org.junit.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JdbcTest {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://vps1:3306/db_micromall";
    private String username = "ct-gogo";
    private String password = "ct-gogo";

    @Test
    public void testJdbc() throws Exception {
        // 1.加载JDBC驱动
        Class.forName(driver);
        // 2.获取数据库连接
        Connection connection = DriverManager.getConnection(url, username, password);
        // connection.setAutoCommit(false);
        String sql = "select * from user where id = ? ";
        // 3.创建Statement对象（每一个Statement为一次数据库执行请求）
        PreparedStatement stmt = connection.prepareStatement(sql);
        // 4.参数设置
        stmt.setString(1, "d3a6a180-a998-469e-b68f-2c3c38f7594c");
        // 5.执行SQL
        ResultSet rs = stmt.executeQuery();
        // 6.处理结果集
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            Map map = new HashMap();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String columnName = rsmd.getColumnName(i + 1);
                map.put(columnName, rs.getString(columnName));
            }
            System.out.println(map);
        }
        // connection.commit();
        // 7.各种关闭
        if (rs != null) {
            rs.close();
            rs = null;
        }
        if (stmt != null) {
            stmt.close();
            stmt = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
