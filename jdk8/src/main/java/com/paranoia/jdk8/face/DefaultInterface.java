package com.paranoia.jdk8.face;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/28 15:41
 */
public interface DefaultInterface {

    public void sayHello();

    /**
     * 支持默认方法的实现   其实也是一个为了支持stream的无奈之举
     */
    default void sayHi() {
        System.out.println("hi~");
    }
}
