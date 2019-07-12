package com.paranoia.design.工厂模式.case1.store;

import com.paranoia.design.工厂模式.case1.product.ChicagoStyleCheesePizza;
import com.paranoia.design.工厂模式.case1.product.NYStyleCheesePizza;
import com.paranoia.design.工厂模式.case1.product.NYStyleVeggiePizza;
import com.paranoia.design.工厂模式.case1.product.Pizza;

/**
 * @author ZHANGKAI
 * @date 2019/7/11
 * @description
 */
public class ChicagoPizzaStore extends PizzaStore {


    @Override
    Pizza createPizza(String pizzaType) {
        if ("cheese".equals(pizzaType)) {
            return new ChicagoStyleCheesePizza();
        }
        return null;
    }
}
