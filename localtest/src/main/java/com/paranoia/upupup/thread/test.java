package com.paranoia.upupup.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ZHANGKAI
 * @date 2020/7/14
 * @description
 */
public class test {
    public static void main(String[] args) {
        List<String> ss = new ArrayList<>();
        List<String> collect = ss.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);


        do {
            System.out.println("do");
        }while (false);
    }
}
