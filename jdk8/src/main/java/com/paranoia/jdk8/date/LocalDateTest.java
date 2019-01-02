package com.paranoia.jdk8.date;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author ZHANGKAI
 * @date 2018/11/22
 * @description
 */
public class LocalDateTest {

    static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public static void main(String[] args) {
        //test5();
        getDate(5);
        List<String> daysBeforeToday = getDaysBeforeToday(5);
        System.out.println("daysBeforeToday = " + daysBeforeToday);
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

        LocalDateTime ldt2 = LocalDateTime.parse(date, dtf);
        System.out.println(ldt2);

    }

    /**
     * 拿取当前时间向后多少天
     *
     * @param num
     */
    public static void getDate(int num) {
        Calendar time = Calendar.getInstance();
        time.setTime(new Date());
        time.add(Calendar.DATE, num);
        Date time1 = time.getTime();
        System.out.println("time1 = " + sdf.get().format(time1));
    }

    /**
     * 拿取当前时间向后多少天
     *
     * @param num
     */
    public static List<String> getDaysBeforeToday(int num) {
        List<String> list = new ArrayList<>();
        Calendar time = Calendar.getInstance();
        IntStream.rangeClosed(1, num)
                .forEach(i -> {
                    System.out.println("i = " + i);
                    time.setTime(new Date());
                    time.add(Calendar.DATE, i);
                    list.add(sdf.get().format(time.getTime()));
                });
        return list;
    }
}



















