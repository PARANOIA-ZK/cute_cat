package com.paranoia.jdk8.future;

import java.util.Random;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/21 9:55
 */
public class Shop {

    public double getPrice(String product) {
        return 0;
    }


    /**
     * 模拟价格的查询
     *
     * @param product
     * @return
     */
    private double calculatePrice(String product) {
        delay();
        Random random = new Random();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }


    /**
     * 模拟延时服务
     */
    private static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
