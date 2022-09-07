package com.xwwx.douyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 可乐罐
 * @date: 2021/12/26 19:58
 * @description:
 */
@EnableFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DouyinAuthApp {
    public static void main(String[] args) {
        SpringApplication.run(DouyinAuthApp.class,args);
    }
}
