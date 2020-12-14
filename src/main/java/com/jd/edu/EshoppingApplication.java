package com.jd.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jd.edu"})
@MapperScan("com.jd.edu.mapper")
public class EshoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshoppingApplication.class, args);
    }

}
