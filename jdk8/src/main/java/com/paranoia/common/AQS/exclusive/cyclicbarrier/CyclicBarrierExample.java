package com.paranoia.common.AQS.exclusive.cyclicbarrier;

import java.util.concurrent.*;

/**
 * @author ZHANGKAI
 * @date 2019/1/2
 * @description
 */
public class CyclicBarrierExample {
    // 请求的数量
    private static final int threadCount = 10;
    // 需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            threadPool.execute(() -> {
                try {
                    System.out.println("当前编号：" + threadNum + "得到服务");
                    cyclicBarrier.await();
                    System.out.println("当前编号：" + threadNum + "释放服务");
                } catch (BrokenBarrierException e) {
                    //do nothing
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }

}
