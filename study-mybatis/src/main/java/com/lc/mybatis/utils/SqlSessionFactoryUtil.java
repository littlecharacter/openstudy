package com.lc.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtil {
	private static SqlSessionFactory sqlSessionFactory = null;
	
	public static SqlSessionFactory getSqlSessionFactory() {
		if (sqlSessionFactory == null) {			
			String resource = "mybatis/mybatis-config.xml";
			try {
				InputStream inputStream = Resources.getResourceAsStream(resource);
				sqlSessionFactory  = new SqlSessionFactoryBuilder().build(inputStream);
				System.out.println("sqlSessionFactory:" + sqlSessionFactory);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sqlSessionFactory;
	}
}
