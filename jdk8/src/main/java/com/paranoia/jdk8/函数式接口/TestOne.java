package com.paranoia.jdk8.函数式接口;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/29 10:45
 */
public class TestOne {

    public static void main(String[] args) {
        process(() -> System.out.println("This is awesome!"));
    }


    public static void process(Runnable runnable) {
        runnable.run();
    }
}
