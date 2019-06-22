package com.paranoia.design.代理模式.静态代理.case1;

/**
 * @author PARANOIA_ZK
 * @date 2019/6/18 21:41
 * @description : 代理类持有本身实现的接口对象引用，实际操作由这个引用的接口实现对象来实现
 */
public class EatProxy implements Eat {

    Eat eat;


    public EatProxy(Eat eat) {
        this.eat = eat;
    }

    @Override
    public void eat() {

        System.out.println("我是代理在实际方法执行之前的行为");

        //实际操作由eat引用来实现
        eat.eat();

        System.out.println("我是代理在实际方法执行之后的行为");
    }
}
