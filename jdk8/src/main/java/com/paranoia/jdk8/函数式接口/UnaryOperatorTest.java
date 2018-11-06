package com.paranoia.jdk8.函数式接口;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/29 14:49
 */
public class UnaryOperatorTest {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        numIncre(list, (int i) -> ++i).forEach(s -> System.out.println("s = " + s));
    }

    public static List<Integer> numIncre(List<Integer> list, IntUnaryOperator intUnaryOperator) {
        List<Integer> result = new ArrayList<>();
        list.forEach(one -> {
            result.add(intUnaryOperator.applyAsInt(one));
        });
        return result;
    }
}
