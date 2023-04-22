package com.atguigu.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient  // 注册进nacos
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.educenter.mapper")
@SpringBootApplication
public class UceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UceApplication.class, args);
    }
}