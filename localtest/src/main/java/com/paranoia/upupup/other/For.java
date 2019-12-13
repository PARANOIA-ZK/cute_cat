package com.paranoia.upupup.other;

/**
 * @author ZHANGKAI
 * @date 2020/6/23
 * @description
 */
public class For {

    public static void main(String[] args) {
        int test = test();
        System.out.println("test = " + test);
    }

    private static int test() {
        int a = 0;
        for (;;){
            a++;
            if (a==10){
                return a;
            }
        }
    }
}
