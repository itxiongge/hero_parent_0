package com.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.hero.order.dao"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run( OrderApplication.class);
    }
}
