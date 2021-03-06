package com.paranoia.design.观察者模式.case1;

import java.util.Observable;
import java.util.Observer;

/**
 * @author ZHANGKAI
 * @date 2019/7/17
 * @description
 */
public class ObserverOne implements Observer {

    /**
     * 观察者需要持有主题的引用, 这个引用的作用是实现观察者主动拉取主题数据，而不是单纯依赖主题的全量推送
     */
    private Observable observable;

    /**
     * 通过一个构造方法，实现实例化主题对象
     */
    public ObserverOne(Observable observable) {
        this.observable = observable;
    }

    /**
     * 观察者被动更新数据
     * 主题对象数据发生变更调用lookDataChanged()方法之后，这个方法作为订阅者的方法
     * 会处理更新逻辑
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SubjectData) {
            String lookData = (String) arg;
            System.out.println("ObserverOne get lookData = " + lookData);
        }
    }

    /**
     * 观察者主动拉取数据
     * 因为观察者已经通过构造方法含有了主题对象的引用，所以可以实现主动拉取数据
     */
    public String getData() {
        if (observable instanceof SubjectData) {
            SubjectData subjectData = ((SubjectData) observable);
            return subjectData.getLookData();
        }
        return "instance error";
    }
}
