package com.paranoia.jdk8.functioninterface;

/**
 * @author PARANOIA_ZK
 * @date 2019/11/9 15:42
 */
public class InterfaceDemo {

    public static void main(String[] args) {

        FunctionInterface functionInterface = (string) -> string + "你好啊";

        Object s = functionInterface.doIt("张三");

        System.out.println("s = " + s);
    }
}

interface FunctionInterface {

    Object doIt(String name);

}
