package com.hero.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
 * @Author yaxiongliu
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class);
    }
}
