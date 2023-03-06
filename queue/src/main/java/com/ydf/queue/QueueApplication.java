package com.ydf.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ywb
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class QueueApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueueApplication.class,args);
    }
}
