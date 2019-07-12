package com.paranoia.design.工厂模式.case1.product;

/**
 * @author ZHANGKAI
 * @date 2019/7/11
 * @description
 */
public class ChicagoStyleCheesePizza extends Pizza{

    public ChicagoStyleCheesePizza() {
        name = "芝加哥芝士披萨";
        price = "$9.9";
    }

    @Override
    public void cut() {
        System.out.println("芝加哥的披萨切成正方形~");
    }
}
