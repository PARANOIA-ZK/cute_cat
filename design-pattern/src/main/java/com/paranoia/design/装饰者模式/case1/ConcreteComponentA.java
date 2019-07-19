package com.paranoia.design.装饰者模式.case1;

/**
 * @author ZHANGKAI
 * @date 2019/7/18
 * @description ：具体组件
 */
public class ConcreteComponentA implements Component {

    @Override
    public String link() {
        return "ConcreteDecoratorA-->";
    }
}
