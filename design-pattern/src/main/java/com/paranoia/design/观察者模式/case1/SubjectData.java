package com.paranoia.design.观察者模式.case1;

import lombok.Data;

import java.util.Observable;

/**
 * @author ZHANGKAI
 * @date 2019/7/17
 * @description
 */
@Data
public class SubjectData extends Observable {

    /**
     * 观察者订阅的主题数据
     */
    private String lookData;

    /**
     * 主题数据发生变化时调用此方法，同时时间订阅发布
     * @param changeData ：主题变化的数据
     */
    public void lookDataChanged(String changeData) {
        //修改主题数据
        lookData = changeData;
        //设置主题对象数据的改变状态
        this.setChanged();
        //通知所有的观察者
        this.notifyObservers(lookData);
    }
}
