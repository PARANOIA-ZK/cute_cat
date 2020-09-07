package com.paranoia.design.ifelse.case1;

import com.paranoia.design.ifelse.case1.handler.AbstractHandler;

/**
 * @description: 策略 + 模板 + 工厂
 * @author: zhangkai
 * @createDate: 2020/9/6
 * @version: 1.0
 */
public class Test {

    public static void main(String[] args) {

        String method = "methodA";
        AbstractHandler handler = FactoryOne.getByKey(method);
        handler.methodA();
    }
}
