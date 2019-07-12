package com.paranoia.design.工厂模式.case1.product;

/**
 * @author ZHANGKAI
 * @date 2019/7/11
 * @description :  注意这是一个静态类~
 *
 * 要依赖抽象，不要依赖具体类
 * --：不要让高层组件依赖低层组件，而且，不管高层还是低层，“两者”都应该依赖于抽象
 */
public abstract class Pizza {

    String name;

    String price;

    public void bake() {
        System.out.println("先放在烤箱烤三十分钟~");
    }

    public void cut() {
        System.out.println("切成一个圆形~");
    }

    public void box() {
        System.out.println("装在披萨盒子里~");
    }

    public String getName(){
        return name;
    }
}
