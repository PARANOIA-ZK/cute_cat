package com.paranoia.upupup.thread.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2021/1/5
 * @version: 1.0
 */
public class TestOne {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Integer> future = executorService.submit(new CallableTask());
        try {
            System.out.println("future = " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    static class CallableTask implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}
