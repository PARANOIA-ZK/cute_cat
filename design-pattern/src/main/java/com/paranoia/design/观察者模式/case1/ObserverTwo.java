package com.paranoia.design.观察者模式.case1;

import lombok.Data;

import java.util.Observable;
import java.util.Observer;

/**
 * @author ZHANGKAI
 * @date 2019/7/17
 * @description
 */
@Data
public class ObserverTwo implements Observer {

    /**
     * 观察者需要持有主题的引用
     */
    private Observable observable;

    /**
     * 通过一个构造对象，实现实例化主题对象
     */
    public ObserverTwo(Observable observable) {
        this.observable = observable;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SubjectData) {
            String lookData = (String) arg;
            System.out.println("ObserverTwo get lookData = " + lookData);
        }
    }
}
