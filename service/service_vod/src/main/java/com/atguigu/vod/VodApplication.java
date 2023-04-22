package com.atguigu.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient      // 注册服务
@EnableFeignClients         // 服务调用
@ComponentScan(basePackages = {"com.atguigu"})      // 包扫描规则
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)     // 不操作数据库，排除数据库
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
