package com.paranoia.design.装饰者模式.case1;

/**
 * @author ZHANGKAI
 * @date 2019/7/19
 * @description
 */
public class TestDrive {
    public static void main(String[] args) {
        Component component = new ConcreteComponentA();
        System.out.println("component.getDescription() = " + component.getDescription());
        System.out.println("component.link() = " + component.link());


        Component componentB = new ConcreteComponentB();
        componentB = new ConcreteDecoratorA(componentB);
        componentB = new ConcreteDecoratorB(componentB);
        System.out.println("componentB.getDescription() = " + componentB.getDescription());
        System.out.println("componentB.link() = " + componentB.link());
    }
}
