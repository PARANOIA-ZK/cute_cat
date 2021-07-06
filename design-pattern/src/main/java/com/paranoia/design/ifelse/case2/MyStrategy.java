package com.paranoia.design.ifelse.case2;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2020/9/6
 * @version: 1.0
 */
public class MyStrategy {

    public static String methodA(String param) {
        System.out.println("param = " + param);
        return param;
    }


    public static String methodB(String param) {
        System.out.println("i am method b");
        return "";
    }

    public static Object methodA(Object o) {
        return "hahha:" + o;
    }
}
