package com.paranoia.design.观察者模式.case1;

/**
 * @author ZHANGKAI
 * @date 2019/7/17
 * @description : 观察者利用主题的接口向主题注册，而主题利用观察者接口通知观察者
 */
public class TestDrive {

    public static void main(String[] args) {
        //实例化主题对象
        SubjectData subjectData = new SubjectData();
        //实例化观察者，并且构建与主题对象的引用
        ObserverOne observerOne = new ObserverOne(subjectData);
        ObserverTwo observerTwo = new ObserverTwo(subjectData);

        //将一个观察者添加到主题的订阅列表里
        subjectData.addObserver(observerOne);
        subjectData.addObserver(observerTwo);

        //主题尝试修改自身状态
        subjectData.lookDataChanged("今天周三");

        //观察者因为有了主题的引用，因此可以自己主动拉取数据
        String observerOneData = observerOne.getData();
        System.out.println("observerOneData = " + observerOneData);
    }
}
