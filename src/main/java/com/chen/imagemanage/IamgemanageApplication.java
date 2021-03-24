package com.chen.imagemanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.chen.imagemanage.mapper")
@SpringBootApplication
public class IamgemanageApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamgemanageApplication.class, args);
    }

}
