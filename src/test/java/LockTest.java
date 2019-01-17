//import com.bootdo.BootdoApplication;
//import com.bootdo.system.domain.Lock;
//import com.bootdo.system.handler.DistributedLockHandler;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * Copyright (C) 2014-2016 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved.
// *
// * @author Mr.liang
// * @version v1
// * @description PACKAGE_NAME bootdo
// * @serve
// * @module
// * @date 2018/8/8
// * @code
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = BootdoApplication.class)
//public class LockTest {
//    @Autowired
//    private DistributedLockHandler distributedLockHandler;
//    @Test
//    public void Test(){
//        Lock lock = new Lock("ly", "梁洋");
//        System.out.println(lock.getName());
//        System.out.println(lock.getValue());
//        if (distributedLockHandler.tryLock(lock)) {
//            System.out.println("test成功获得锁！！！");
//            doSomething();
//            distributedLockHandler.releaseLock(lock);
//        } else {
//            System.out.println("test获取锁失败！！！");
//        }
//
//    }
//    public static void doSomething() {
//        try {
//            Thread.sleep(5 * 1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    public void Test1(){
//        Lock lock = new Lock("ly", "梁洋");
//        System.out.println(lock.getName());
//        System.out.println(lock.getValue());
//        if (distributedLockHandler.tryLock(lock)) {
//            System.out.println("test1成功获得锁！！！");
//            doSomething();
//            distributedLockHandler.releaseLock(lock);
//        } else {
//            System.out.println("test1获取锁失败！！！");
//        }
//
//    }
//}
