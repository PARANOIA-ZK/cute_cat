package com.paranoia.upupup.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZHANGKAI
 * @date 2020/7/14
 * @description
 */
public class ConditionCase {

    public static final Lock LOCK = new ReentrantLock();
    public static final Condition CONDITION = LOCK.newCondition();

    public static void main(String[] args) throws InterruptedException {
//        lockInterruptibly();
        Thread one = new Thread(ConditionCase::conditionWait, "线程1");
        Thread two = new Thread(ConditionCase::conditionSignal, "线程2");
        one.start();
        Thread.sleep(1000);
        two.start();
    }

    public static void conditionWait() {
        LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "等待之前执行业务");
            CONDITION.await();
            System.out.println(Thread.currentThread().getName() + "等待之后执行业务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    public static void conditionSignal() {
        LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "释放之前执行业务");
            CONDITION.signal();
            System.out.println(Thread.currentThread().getName() + "释放之后执行业务");
        } finally {
            LOCK.unlock();
        }
    }


    public static void lockInterruptibly() throws InterruptedException {
        LOCK.lockInterruptibly();
        try {
            System.out.println("主线程开始中断");
            Thread.sleep(1000);
            System.out.println("主线程结束中断");
        } finally {
            LOCK.unlock();
        }
    }
}




















