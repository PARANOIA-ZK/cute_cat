package com.paranoia.design.ifelse.case1.handler;

import com.paranoia.design.ifelse.case1.FactoryOne;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2020/9/6
 * @version: 1.0
 */
@Component
public class HandlerA extends AbstractHandler {


    @Override
    public void methodA() {
        System.out.println("i am methodA");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FactoryOne.register("handlerA", this);
    }
}
