package com.paranoia.upupup.collection;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author ZHANGKAI
 * @date 2019/7/16
 * @description
 */
public class ArrayListTest {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, 1, 2, 3);

        ArrayList copy = ((ArrayList) arrayList.clone());

        System.out.println("arrayList = " + arrayList);
        System.out.println("copy = " + copy);

        arrayList.remove(1);

        System.out.println("arrayList = " + arrayList);
        System.out.println("copy = " + copy);

        System.out.println("====================================================");

        ArrayList<People> peopleArrayList = new ArrayList<>();
        Info info = new Info();
        info.setAge("100");
        info.setAddress("皇后大道");
        peopleArrayList.add(new People("黄色网线", info));
        peopleArrayList.add(new People("绿色网线", info));


        ArrayList peopleArrayListCopy = ((ArrayList) peopleArrayList.clone());

        System.out.println("peopleArrayList.get(0) = " + peopleArrayList.get(0));
        System.out.println("peopleArrayListCopy.get(0) = " + peopleArrayListCopy.get(0));

        peopleArrayList.get(0).getInfo().setAddress("东厂十三街");
        peopleArrayList.get(0).setName("张无忌");
        System.out.println("看下修改之后的数据");

        System.out.println("peopleArrayList.get(0) = " + peopleArrayList.get(0));
        System.out.println("peopleArrayListCopy.get(0) = " + peopleArrayListCopy.get(0));

        ((People) peopleArrayListCopy.get(0)).getInfo().setAddress("copy修改东厂十三街");
        ((People) peopleArrayListCopy.get(0)).setName("copy修改张无忌");
        System.out.println("看下修改之后的数据");

        System.out.println("peopleArrayList.get(0) = " + peopleArrayList.get(0));
        System.out.println("peopleArrayListCopy.get(0) = " + peopleArrayListCopy.get(0));

    }
}

@Data
class People {
    /***/
    private String name;

    private Info info;

    public People(String name, Info info) {
        this.name = name;
        this.info = info;
    }
}

@Data
class Info {
    /***/
    private String age;

    /***/
    private String address;
}
