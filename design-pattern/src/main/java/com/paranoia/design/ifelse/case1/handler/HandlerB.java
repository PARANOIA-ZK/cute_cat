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
public class HandlerB extends AbstractHandler {


    @Override
    public void methodB() {
        System.out.println("i am methodB");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FactoryOne.register("handlerB", this);
    }
}
