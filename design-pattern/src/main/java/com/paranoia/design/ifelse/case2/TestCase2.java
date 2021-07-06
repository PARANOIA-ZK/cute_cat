package com.paranoia.design.ifelse.case2;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2020/9/6
 * @version: 1.0
 */
public class TestCase2 {
    public static void main(String[] args) {
        String apply = StrategyEnum.getByMethod("methodA").apply("你好");
        System.out.println(apply);
    }
}
