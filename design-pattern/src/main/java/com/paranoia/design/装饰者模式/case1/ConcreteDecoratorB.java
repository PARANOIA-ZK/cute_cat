package com.paranoia.design.装饰者模式.case1;

/**
 * @author ZHANGKAI
 * @date 2019/7/18
 * @description : 具体装饰者
 */
public class ConcreteDecoratorB implements Decorator {

    private Component component;

    public ConcreteDecoratorB(Component component) {
        this.component = component;
    }

    @Override
    public String getDescription() {
        return component.getDescription() + "-->我是包装者A的描述";
    }

    @Override
    public String link() {
        return "ConcreteDecoratorB-->" + component.link();
    }
}
