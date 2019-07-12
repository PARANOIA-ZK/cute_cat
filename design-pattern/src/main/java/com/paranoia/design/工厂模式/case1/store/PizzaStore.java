package com.paranoia.design.工厂模式.case1.store;

import com.paranoia.design.工厂模式.case1.product.Pizza;

/**
 * @author ZHANGKAI
 * @date 2019/7/11
 * @description
 */
public abstract class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza;

        /**
         * 高层组件(PizzaStore)依赖的低层组件(Pizza)是一个抽象依赖
         */
        pizza = createPizza(type);

        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    /**
     * 在这个抽象类当中，任何其它实现的方法，都可能使用到这个工厂方法所制造出来的产品，但只有子类真正实现这个工厂方法并创建产品。
     *
     * 工厂方法让子类决定要实例化的类是哪一个。这里的“决定”,并不是说允许子类在运行时做决定，而是创建者（PizzaStore）在调用工厂方法的
     * 时候，由一些业务条件决定创建哪一个子类，自然而然就决定了实际创建的产品(Pizza)是什么。
     */
    abstract Pizza createPizza(String pizzaType);
}
