package com.paranoia.upupup.other;

/**
 * @author ZHANGKAI
 * @date 2019/12/13
 * @description
 */
public class DengYu {

    public static  String str = "Old String";
    public static  Integer integer = 0;
    public static  int anInt = 0;

    public static void main(String[] args) {
        String newString = str;
        str = "Old String 132";
        System.out.println("newString = " + newString);


        Integer newInteger = integer;
        integer = 1;
        System.out.println("newInteger = " + newInteger);

        int newInt = anInt;
        anInt = 1;
        System.out.println("newInt = " + newInt);
    }
}
