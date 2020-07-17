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

    public static void main(String[] args) {


    }


    public static void conditionWait() throws InterruptedException {
        LOCK.lock();
        try {
            CONDITION.await();
        } finally {
            LOCK.unlock();
        }
    }

    public static void conditionSignal() throws InterruptedException {
        LOCK.lock();
        try {
            CONDITION.signal();
        } finally {
            LOCK.unlock();
        }
    }

}




















