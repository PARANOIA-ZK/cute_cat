package com.paranoia.upupup.function;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2020/11/23
 * @version: 1.0
 */
public class FunctionTest {
    public static void main(String[] args) {
        List<String> collect = IntStream.range(0, 5)
                .boxed()
                .map(FunctionOne::apply)
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);
    }
}
