package com.paranoia.upupup.thread;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.locks.LockSupport;

/**
 * @author ZHANGKAI
 * @date 2020/6/30
 * @description
 */
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        ObjectTar objectTar = new ObjectTar();
        ParkThread parkThread = new ParkThread("ParkThread", objectTar);
        parkThread.start();
        Thread.sleep(1000);
        new UnParkThread("UnParkThread", objectTar, parkThread).start();
    }

    @Data
    private static class ObjectTar {
        private String name;
    }


    private static class ParkThread extends Thread {

        final ObjectTar objectTar;

        ParkThread(String name, ObjectTar tar) {
            super(name);
            this.objectTar = tar;
        }

        @Override
        public void run() {
            System.out.println(getName() + "获取锁，准备执行自己的业务逻辑");
            if (StringUtils.isEmpty(objectTar.getName())) {
                System.out.println(getName() + "发现目前的状况不能满足自己的要求，于是执行LockSupport.park()");
                LockSupport.park();
            }
            System.out.println(getName() + "从LockSupport.park()返回，完成自己的业务逻辑，" + objectTar.getName());
        }
    }

    private static class UnParkThread extends Thread {

        final ObjectTar objectTar;

        Thread parkThread;

        UnParkThread(String name, ObjectTar tar, Thread parkThread) {
            super(name);
            this.objectTar = tar;
            this.parkThread = parkThread;
        }

        @Override
        public void run() {
            System.out.println(getName() + "获取锁，完成了objectTar的name初始化");
            objectTar.setName("黄焖鸡");
            LockSupport.unpark(parkThread);
            System.out.println(getName() + "执行完LockSupport.unpark()之后，继续执行自己的业务逻辑");
        }
    }
}
