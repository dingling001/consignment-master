package com.bootdo.system.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Copyright (C) 2014-2016 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved.
 *
 * @author Mr.liang
 * @version v1
 * @description com.bootdo.system.listener bootdo
 * @serve
 * @module
 * @date 2018/8/31
 * @code
 */
@WebListener
public class CouponCodeExpiredListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
