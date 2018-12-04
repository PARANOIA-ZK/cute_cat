package com.paranoia.jdk8.date;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ZHANGKAI
 * @date 2018/11/22
 * @description
 */
public class LocalDateTest {


    public static void main(String[] args) {
        test5();
    }

    /**
     * DateTimeFormatter 解析和格式化日期或时间
     */
    public static void test5() {


        //DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss E");

        LocalDateTime ldt = LocalDateTime.now();

        String date = ldt.format(dtf);
        System.out.println(date);

        LocalDateTime ldt2 = ldt.parse(date, dtf);
        System.out.println(ldt2);

    }
}
