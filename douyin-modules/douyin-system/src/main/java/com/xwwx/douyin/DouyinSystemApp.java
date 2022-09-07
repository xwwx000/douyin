package com.xwwx.douyin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: 可乐罐
 * @date: 2021/12/25 0:16
 * @description:
 */
@EnableAsync
@SpringBootApplication
@EnableFeignClients
public class DouyinSystemApp {
    public static void main(String[] args) {
        SpringApplication.run(com.xwwx.douyin.DouyinSystemApp.class,args);
    }
}
