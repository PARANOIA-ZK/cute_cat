package com.paranoia.upupup.collection;


import java.util.HashMap;

/**
 * @author ZHANGKAI
 * @date 2019/12/16
 * @description
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i),String.valueOf(i));
        }
        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i),String.valueOf(++i));
        }
        System.out.println("map = " + map.size());
    }
}
