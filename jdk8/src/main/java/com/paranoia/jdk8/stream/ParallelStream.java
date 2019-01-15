package com.paranoia.jdk8.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author PARANOIA_ZK
 * @date 2019/1/12 13:44
 */
public class ParallelStream {

    public static void main(String[] args) {
        //parallelStream();
        parallelStreamUseMyThreadPool();
    }

    /**
     * 单线程流
     */
    private static void singelStream() {

        IntStream.range(1, 100)
                 .peek(ParallelStream::outNum)
                 .count();
    }

    /**
     * 多线程流
     */
    private static void parallelStream() {
        //修改默认线程数
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "10");

        IntStream.range(1, 100)
                 .parallel()
                 .peek(ParallelStream::outNum)
                 .count();
    }

    /**
     * 使用靠自己的线程池，不使用默认线程池，防止重要业务被阻塞
     */
    private static void parallelStreamUseMyThreadPool() {

        ForkJoinPool myPool = new ForkJoinPool(20);

        myPool.submit(() ->
                IntStream.range(1, 100)
                         .parallel()
                         .peek(ParallelStream::outNum)
                         .count()
        );
        myPool.shutdown();

        //防止主线程退出
        synchronized (myPool){
            try {
                myPool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }









    private static void outNum(int num) {
        System.out.println(Thread.currentThread().getName() + "-----num = " + num);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
