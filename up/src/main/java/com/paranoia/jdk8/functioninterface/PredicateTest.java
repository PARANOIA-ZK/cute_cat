package com.paranoia.jdk8.functioninterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/29 11:16
 */
public class PredicateTest {


    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<T>();
        list.forEach(one -> {
            if (predicate.test(one)) {
                result.add(one);
            }
        });
        return result;
    }


    public static void main(String[] args) {
        Predicate<String> predicate = (String s) -> !s.isEmpty();

        List<String> stringList = new ArrayList<>();
        Collections.addAll(stringList, "123", "qwwerqw", "fdwf", "null", "");

        filter(stringList, predicate).forEach(System.out::println);
    }
}
