package com.lc.springboot;

import com.lc.springboot.annotation.EnableOrpc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.lc.springboot.mapper")
// @PropertySource(value = {"classpath:openrpc.properties"})
@EnableOrpc(path = "config/openrpc.properties")
public class StudySpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudySpringbootApplication.class, args);
	}

}
