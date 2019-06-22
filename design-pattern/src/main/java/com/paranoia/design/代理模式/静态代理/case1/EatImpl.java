package com.paranoia.design.代理模式.静态代理.case1;

/**
 * @author PARANOIA_ZK
 * @date 2019/6/18 21:40
 *
 * 这是服务类
 */
public class EatImpl implements Eat{
    @Override
    public void eat() {
        System.out.println("我开始吃东西了");
    }
}
