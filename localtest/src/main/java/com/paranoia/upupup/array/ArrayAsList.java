package com.paranoia.upupup.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/12/6
 * @description
 */
public class ArrayAsList {

    public static void main(String[] args) {
        List oldWorkDay = Arrays.asList("周一;周二;周三".split(";"));
        List newWorkDay = Arrays.asList("周三;周四;周五".split(";"));
        System.out.println("newWorkDay = " + newWorkDay);
        System.out.println("oldWorkDay = " + oldWorkDay);

        List add = new ArrayList();
        List remove = new ArrayList();

        oldWorkDay.forEach(old -> {
            if (!newWorkDay.contains(old)) {
                remove.add(old);
            }
        });
        newWorkDay.forEach(old -> {
            if (!oldWorkDay.contains(old)) {
                add.add(old);
            }
        });
        System.out.println("add = " + add);
        System.out.println("remove = " + remove);
    }
}
