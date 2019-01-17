package com.bootdo.system.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Copyright (C) 2014-2016 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved.
 *
 * @author Mr.liang
 * @version v1
 * @description com.bootdo.system.config bootdo
 * @serve
 * @module
 * @date 2018/8/30
 * @code
 */
@Configuration
public class MultipartConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 置文件大小限制 ,超出此大小页面会抛出异常信息
        factory.setMaxFileSize("2MB"); //KB,MB
        // 设置总上传数据总大小
        factory.setMaxRequestSize("20MB");
        // 设置文件临时文件夹路径
        // factory.setLocation("E://test//");
        // 如果文件大于这个值，将以文件的形式存储，如果小于这个值文件将存储在内存中，默认为0
        // factory.setMaxRequestSize(0);
        return factory.createMultipartConfig();
    }
}
