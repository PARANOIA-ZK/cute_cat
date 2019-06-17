package com.paranoia.design.代理模式.保护代理.case1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author PARANOIA_ZK
 * @date 2019/6/17 22:36
 */
public class OwnerInvocationHandler implements InvocationHandler {

    private PersonBean personBean;

    OwnerInvocationHandler(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //自己的数据，当然是随便看，随便改
        return method.invoke(personBean,args);
    }
}
