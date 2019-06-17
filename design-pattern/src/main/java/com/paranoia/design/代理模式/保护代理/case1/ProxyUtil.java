package com.paranoia.design.代理模式.保护代理.case1;

import java.lang.reflect.Proxy;

/**
 * @author PARANOIA_ZK
 * @date 2019/6/17 22:48
 */
public class ProxyUtil {

    public static PersonBean getOwnerProxy(PersonBean personBean){
        return (PersonBean) Proxy.newProxyInstance(
                personBean.getClass().getClassLoader(),
                personBean.getClass().getInterfaces(),
                new OwnerInvocationHandler(personBean)
        );
    }


    public static PersonBean getNotOwnerProxy(PersonBean personBean){
        return (PersonBean) Proxy.newProxyInstance(
                personBean.getClass().getClassLoader(),
                personBean.getClass().getInterfaces(),
                new NotOwnerInvocationHandler(personBean)
        );
    }
}
