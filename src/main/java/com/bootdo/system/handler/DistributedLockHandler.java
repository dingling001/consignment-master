//package com.bootdo.system.handler;
//
//import com.bootdo.system.domain.Lock;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * Copyright (C) 2014-2016 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved.
// *
// * @author Mr.liang
// * @version v1
// * @description com.bootdo.system.handler bootdo
// * @serve
// * @module
// * @date 2018/8/8
// * @code
// */
//@Component
//public class DistributedLockHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(DistributedLockHandler.class);
//    /**
//     * 单个业务持有锁的时间30s，防止死锁
//     */
//    private static final long LOCK_EXPIRE = 30 * 1000L;
//    /**
//     * 默认30ms重试一次
//     */
//    private static final long LOCK_TRY_INTERVAL = 30L;
//    /**
//     * 默认尝试20s
//     */
//    private static final long LOCK_TRY_TIMEOUT = 20 * 1000L;
//
//    @Autowired
//    private StringRedisTemplate template;
//
//    /**
//     * 尝试获取全局锁
//     *
//     * @param lock 锁的名称
//     * @return true 获取成功，false获取失败
//     */
//    public boolean tryLock(Lock lock) {
//        logger.debug("进入获取锁的方法中->锁名：{},锁值：{}",lock.getName(),lock.getValue());
//        return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
//    }
//
//    /**
//     * 尝试获取全局锁
//     *
//     * @param lock    锁的名称
//     * @param timeout 获取超时时间 单位ms
//     * @return true 获取成功，false获取失败
//     */
//    public boolean tryLock(Lock lock, long timeout) {
//        return getLock(lock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
//    }
//
//    /**
//     * 尝试获取全局锁
//     *
//     * @param lock        锁的名称
//     * @param timeout     获取锁的超时时间
//     * @param tryInterval 多少毫秒尝试获取一次
//     * @return true 获取成功，false获取失败
//     */
//    public boolean tryLock(Lock lock, long timeout, long tryInterval) {
//        return getLock(lock, timeout, tryInterval, LOCK_EXPIRE);
//    }
//
//    /**
//     * 尝试获取全局锁
//     *
//     * @param lock           锁的名称
//     * @param timeout        获取锁的超时时间
//     * @param tryInterval    多少毫秒尝试获取一次
//     * @param lockExpireTime 锁的过期
//     * @return true 获取成功，false获取失败
//     */
//    public boolean tryLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
//        return getLock(lock, timeout, tryInterval, lockExpireTime);
//    }
//
//    /**
//     * 操作redis获取全局锁
//     *
//     * @param lock           锁的名称
//     * @param timeout        获取的超时时间
//     * @param tryInterval    多少ms尝试一次
//     * @param lockExpireTime 获取成功后锁的过期时间
//     * @return true 获取成功，false获取失败
//     */
//    private boolean getLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
//        try {
//            if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) {
//                return false;
//            }
//            long startTime = System.currentTimeMillis();
//            logger.debug("执行do-while之前");
//            do {
//                if (!template.hasKey(lock.getName())) {
//                    ValueOperations<String, String> ops = template.opsForValue();
//                    ops.set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
//                    return true;
//                } else {//存在锁
//                    logger.debug("此锁已经存在！！！");
//                }
//                if (System.currentTimeMillis() - startTime > timeout) {//尝试超过了设定值之后直接跳出循环
//                    logger.debug("尝试超过了设定值之后直接跳出循环");
//                    return false;
//                }
//                Thread.sleep(tryInterval);
//            }
//            while (template.hasKey(lock.getName()));
//            logger.debug("do-while结束");
//        } catch (InterruptedException e) {
//            logger.error(e.getMessage());
//            return false;
//        }
//        logger.debug("跳出try-catch");
//        return false;
//    }
//
//    /**
//     * 释放锁
//     */
//    public void releaseLock(Lock lock) {
//        if (!StringUtils.isEmpty(lock.getName())) {
//            template.delete(lock.getName());
//        }
//    }
//
////    public static void main(String[] args) {
////        DistributedLockHandler distributedLockHandler = new DistributedLockHandler();
////        Lock lock = new Lock("ly", "梁洋");
////        if (distributedLockHandler.tryLock(lock)) {
////            doSomething();
////            distributedLockHandler.releaseLock(lock);
////        }
////    }
//
//    public static void doSomething() {
//        for (int i = 0; i < 1000000; i++) {
//            System.out.println("循环了：" + i + "次");
//        }
//    }
//}
