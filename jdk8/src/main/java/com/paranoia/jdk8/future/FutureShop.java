package com.paranoia.jdk8.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/21 9:55
 */
public class FutureShop {

    /**
     * 使用自定义线程池可以提高异步的效率  否则默认的就是Runtime.getRuntime().availableProcessors()
     */
    private static final Executor executor = Executors.newFixedThreadPool(Math.min(50, 100),
            r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });

    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());


        long start = System.nanoTime();

        Future<Double> future = getPriceAsync("dog", executor);
        doSomethingElse();

        try {
            double price = future.get(4, TimeUnit.SECONDS);
            System.out.println("price = " + price);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }

        long end = System.nanoTime();
        System.out.println("spend time :" + (end - start) / 1_000_000);

    }

    private static void doSomethingElse() {
        try {
            System.out.println("do something else");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Future<Double> getPriceAsync(String product, Executor executor) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product), executor);
    }


    /**
     * 模拟价格的查询
     *
     * @param product
     * @return
     */
    private static double calculatePrice(String product) {
        delay();
        Random random = new Random();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }


    /**
     * 模拟延时服务
     */
    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
