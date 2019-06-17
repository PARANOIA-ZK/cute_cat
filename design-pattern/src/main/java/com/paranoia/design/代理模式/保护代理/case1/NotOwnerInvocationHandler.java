package com.paranoia.design.代理模式.保护代理.case1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author PARANOIA_ZK
 * @date 2019/6/17 22:36
 */
public class NotOwnerInvocationHandler implements InvocationHandler {

    private PersonBean personBean;

    NotOwnerInvocationHandler(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getIdCard".equals(method.getName())){
            throw new IllegalArgumentException("又不是你的资料，你瞅啥");
        }
        return method.invoke(personBean,args);
    }
}
