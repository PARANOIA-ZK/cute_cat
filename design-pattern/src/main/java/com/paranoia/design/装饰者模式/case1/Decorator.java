package com.paranoia.design.装饰者模式.case1;

/**
 * @author ZHANGKAI
 * @date 2019/7/18
 * @description ： 抽象装饰者
 */
public interface Decorator extends Component {

    /**
     * 装饰者覆盖这个方法添加自己的属性定义，或者说是扩展这个方法
     * @return
     */
    @Override
    String getDescription();
}
