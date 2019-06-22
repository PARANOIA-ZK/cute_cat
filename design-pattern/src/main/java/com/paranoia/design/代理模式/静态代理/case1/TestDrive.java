package com.paranoia.design.代理模式.静态代理.case1;

/**
 * @author PARANOIA_ZK
 * @date 2019/6/18 21:45
 */

/**
 * 静态代理总结：
 *
 * 优点：可以做到在符合开闭原则的情况下对目标对象进行功能扩展。
 *
 * 缺点：我们得为每一个服务都得创建代理类，工作量太大，不易管理。同时接口一旦发生改变，代理类也得相应修改。
 */
public class TestDrive {

    public static void main(String[] args) {
        Eat eat = new EatImpl();

        EatProxy eatProxy = new EatProxy(eat);

        eatProxy.eat();
    }
}
