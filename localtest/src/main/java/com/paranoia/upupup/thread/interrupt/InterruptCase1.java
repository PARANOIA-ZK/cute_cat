package com.paranoia.upupup.thread.interrupt;

/**
 * @author ZHANGKAI
 * @date 2020/7/20
 * @description
 */
public class InterruptCase1 {
    public static void main(String[] args) throws InterruptedException {
        CaseThread caseThread = new CaseThread();
        caseThread.start();
        Thread.sleep(5);

        caseThread.interrupt();
    }


    public static class CaseThread extends Thread{
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("线程正在执行");
            }
            System.out.println("====线程被中断");
        }
    }
}
