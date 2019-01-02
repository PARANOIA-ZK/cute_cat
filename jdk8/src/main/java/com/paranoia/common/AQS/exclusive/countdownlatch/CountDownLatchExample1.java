package com.paranoia.common.AQS.exclusive.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZHANGKAI
 * @date 2019/1/2
 * @description
 */
public class CountDownLatchExample1 {
    // 请求的数量
    private static final int threadCount = 30;

    public static void main(String[] args) throws InterruptedException {
        // 创建一个具有固定线程数量的线程池对象（如果这里线程池的线程数量给太少的话你会发现执行的很慢）
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                System.out.println("threadNum = " + threadNum);
                countDownLatch.countDown();// 表示一个请求已经被完成
            });
        }
        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("finish");
    }
}
