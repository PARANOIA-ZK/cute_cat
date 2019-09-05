package com.paranoia.upupup.collection;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author ZHANGKAI
 * @date 2019/9/3
 * @description
 */
public class HashTableTest {

    public static void main(String[] args) throws InterruptedException {
        deadCycle();
    }

    public static void deadCycle() throws InterruptedException {
        final HashMap<String, String> map = new HashMap<>(2);
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                new Thread(() -> map.put(UUID.randomUUID().toString(), ""), "ftf" + i).start();
            }
        }, "ftf");
        t.start();
        t.join();
    }
}
