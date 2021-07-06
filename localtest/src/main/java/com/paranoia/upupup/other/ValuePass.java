package com.paranoia.upupup.other;

import lombok.Data;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2021/3/5
 * @version: 1.0
 */
public class ValuePass {

    public static void main(String[] args) {
        String ss = "OLD";
        System.out.println("change(ss) = " + change(ss));

        Student student = new Student();
        student.setName("OLD");
        System.out.println("change(ss) = " + change(student).getName());

    }

    private static String change(String ss) {
        ss = "new";
        return ss;
    }

    private static Student  change(Student ss) {
       ss.setName("new");
        return ss;
    }

    @Data
    private static class Student {
        String name;
    }
}
