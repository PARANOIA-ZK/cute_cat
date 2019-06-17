package com.paranoia.design.代理模式.保护代理.case1;

import java.util.HashMap;

/**
 * @author PARANOIA_ZK
 * @date 2019/6/17 22:52
 */
public class TestDrive {

    private static final HashMap<String, PersonBean> DATA_BASE = new HashMap<>();

    public static void main(String[] args) {
        TestDrive testDrive = new TestDrive();
        testDrive.drive();
    }

    private TestDrive() {
        initializeData();
    }

    private void initializeData() {
        DATA_BASE.put("tom", new PersonBeanImpl("tom", "10", "10086"));
    }

    private PersonBean getDataFromDataBase(String name){
        return DATA_BASE.get(name);
    }

    private void drive(){
        PersonBean tom = getDataFromDataBase("tom");

        PersonBean ownerProxy = ProxyUtil.getOwnerProxy(tom);
        System.out.println("ownerProxy.getIdCard() = " + ownerProxy.getIdCard());


        PersonBean notOwnerProxy = ProxyUtil.getNotOwnerProxy(tom);
        try {
            System.out.println("notOwnerProxy = " + notOwnerProxy.getIdCard());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
