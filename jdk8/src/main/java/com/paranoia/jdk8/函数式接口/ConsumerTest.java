package com.paranoia.jdk8.函数式接口;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/29 13:23
 */
public class ConsumerTest {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        forEach(list, (Integer i) -> System.out.println("i = " + i));

    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        list.forEach(consumer::accept);
    }
}
