package com.paranoia.upupup.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2021/6/24
 * @version: 1.0
 */
public class TestSingleTS {

    static ScheduledExecutorService executorService;

    static {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public static void main(String[] args) {
        System.out.println("main:" + test());
    }

    private static String test() {
        System.out.println("begin");

        String ss = "ss";

        executorService.schedule(() -> {
            System.out.println("ss = " + ss);
        }, 5, TimeUnit.SECONDS);

        System.out.println("end");
        return "method end";
    }
}
