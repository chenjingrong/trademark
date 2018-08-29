package com.uz83.trademark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.uz83")
@MapperScan("com.uz83.trademark.mapper")
public class TrademarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrademarkApplication.class, args);
	}
}
