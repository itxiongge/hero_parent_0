package com.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.hero.business.dao"})
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run( BusinessApplication.class);
    }
}
