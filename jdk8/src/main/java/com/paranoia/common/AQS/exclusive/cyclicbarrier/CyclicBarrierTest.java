package com.paranoia.common.AQS.exclusive.cyclicbarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author ZHANGKAI
 * @date 2019/1/2
 * @description
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        CyclicBarrier barrier = new CyclicBarrier(5);//参数为阻塞线程
        List<String> nameList = new ArrayList<>();
        Collections.addAll(nameList, "-----盲僧-----", "-----剑圣-----", "-----卡莎-----", "-----风男-----", "-----蠢劫-----","-----大嘴-----","-----蛤蟆-----");
        for (int i = 0; i < 5; i++) {
            service.execute(new Player("玩家" + nameList.get(0), barrier));
            nameList.remove(0);
        }
        service.shutdown();
    }
}
class Player implements Runnable {
    private final String name;
    private final CyclicBarrier barrier;
    Player(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
    }
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1 + (new Random().nextInt(3)));
            System.out.println(name + "已准备,等待其他玩家准备...");
            barrier.await();
            TimeUnit.SECONDS.sleep(1 + (new Random().nextInt(3)));
            System.out.println(name + "已加入游戏");
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println(name + "离开游戏");
        }
    }

}
