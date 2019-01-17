package com.bootdo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringCloudApplication
@EnableTransactionManagement
@ServletComponentScan
@MapperScan({"com.bootdo.*.dao","com.bootdo.*.*.*.dao"})
@SpringBootApplication
@EnableCaching
public class BootdoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootdoApplication.class, args);
//        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    bootdo启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
//                " ______                    _   ______            \n" +
//                "|_   _ \\                  / |_|_   _ `.          \n" +
//                "  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n" +
//                "  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n" +
//                " _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n" +
//                "|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ");
//        String cmd = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe http://127.0.0.1";
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            runtime.exec(cmd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
