package com.paranoia.design.装饰者模式.case1;

/**
 * @author ZHANGKAI
 * @date 2019/7/18
 * @description : 具体装饰者
 */
public class ConcreteDecoratorA implements Decorator {

    private Component component;

    /**
     * 要让Mocha能够引用一个Beverage，做法如下：
     * (1)用一个实例变量记录饮料，也就是被装饰者。
     * (2)想办法让被装饰者（饮料）被记录到实例变量中。
     * 这里的做法是：把饮料当作构造器的参数，再由构造器将此饮料记录在实例变量中。
     */
    public ConcreteDecoratorA(Component component) {
        this.component = component;
    }

    @Override
    public String getDescription() {
        return component.getDescription() + "-->i am ConcreteDecoratorA";
    }

    @Override
    public String link() {
        return "ConcreteDecoratorA-->" + component.link();
    }
}
