package com.paranoia.upupup.thread;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ZHANGKAI
 * @date 2020/6/30
 * @description
 */
public class WaitNotifyTest {

    public static void main(String[] args) throws InterruptedException {
        ObjectTar objectTar = new ObjectTar();
        new WaitThread("WaitThread", objectTar).start();
        Thread.sleep(1000);
        new NotifyThread("NotifyThread", objectTar).start();
    }

    @Data
    private static class ObjectTar {
        private String name;
    }


    private static class WaitThread extends Thread {

        final ObjectTar objectTar;

        WaitThread(String name, ObjectTar tar) {
            super(name);
            this.objectTar = tar;
        }

        @Override
        public void run() {
            synchronized (objectTar) {
                System.out.println(getName() + "获取锁，准备执行自己的业务逻辑");
                if (StringUtils.isEmpty(objectTar.getName())) {
                    System.out.println(getName() + "发现目前的状况不能满足自己的要求，于是执行objectTar.wait()");
                    try {
                        objectTar.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(getName() + "从objectTar.wait()返回，完成自己的业务逻辑，" + objectTar.getName());
            }
        }
    }

    private static class NotifyThread extends Thread {

        final ObjectTar objectTar;

        NotifyThread(String name, ObjectTar tar) {
            super(name);
            this.objectTar = tar;
        }

        @Override
        public void run() {
            synchronized (objectTar) {
                System.out.println(getName() + "获取锁，完成了objectTar的name初始化");
                objectTar.setName("黄焖鸡");
                objectTar.notifyAll();
                System.out.println(getName() + "执行完notify之后，继续执行自己的业务逻辑");
            }
        }
    }
}
