package com.paranoia.design.工厂模式.case1;

import com.paranoia.design.工厂模式.case1.product.Pizza;
import com.paranoia.design.工厂模式.case1.store.ChicagoPizzaStore;
import com.paranoia.design.工厂模式.case1.store.NYPizzaStore;
import com.paranoia.design.工厂模式.case1.store.PizzaStore;

/**
 * @author ZHANGKAI
 * @date 2019/7/11
 * @description
 */
public class Drive {

    public static void main(String[] args) {
        PizzaStore nyPizzaStore = new NYPizzaStore();

        Pizza nyPizza = nyPizzaStore.orderPizza("cheese");
        System.out.println("这是我点的pizza:" + nyPizza.getName());

        ChicagoPizzaStore chicagoPizzaStore = new ChicagoPizzaStore();
        Pizza chicagoPizza = chicagoPizzaStore.orderPizza("cheese");
        System.out.println("chicagoPizza = " + chicagoPizza.getName());
    }
}
