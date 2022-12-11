package com.xxxx.crm;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/2 10 :27
 * @Version:v1.0
 */
@SpringBootApplication
@MapperScan("com.xxxx.crm.dao")
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
