package com.paranoia.design.装饰者模式.case1;

/**
 * @author ZHANGKAI
 * @date 2019/7/18
 * @description : 抽象组件
 */
public interface Component {

    String DESCRIPTION = "我是component";

    default String getDescription(){
        return DESCRIPTION;
    }

    /**
     * 装饰着扩展的行为
     */
    String link();
}
