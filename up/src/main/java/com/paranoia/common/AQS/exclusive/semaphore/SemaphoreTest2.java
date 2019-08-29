package com.paranoia.common.AQS.exclusive.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author ZHANGKAI
 * @date 2019/1/2
 * @description
 */
public class SemaphoreTest2 {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final Semaphore semaphore = new Semaphore(5, true);
        for (int i = 0; i < 20; i++) {
            int finalX = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("当前编号：" + finalX + "得到服务");
                    Thread.sleep((long) (Math.random() * 2000));
                    System.out.println("当前编号：" + finalX + "释放服务");
                    semaphore.release();
                    System.out.println("---------------当前可用窗口：" + semaphore.availablePermits());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
