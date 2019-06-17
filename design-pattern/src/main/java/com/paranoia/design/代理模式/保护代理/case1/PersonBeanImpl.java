package com.paranoia.design.代理模式.保护代理.case1;

/**
 * @author PARANOIA_ZK
 * @date 2019/6/17 22:32
 */
public class PersonBeanImpl implements PersonBean {

    private String name;

    private String age;

    private String idCard;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAge() {
        return age;
    }

    @Override
    public String getIdCard() {
        return idCard;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    public PersonBeanImpl(String name, String age, String idCard) {
        this.name = name;
        this.age = age;
        this.idCard = idCard;
    }
}
