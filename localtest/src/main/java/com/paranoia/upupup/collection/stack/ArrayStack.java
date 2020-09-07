package com.paranoia.upupup.collection.stack;

/**
 * @description: 数组实现的顺序栈
 * @author: zhangkai
 * @createDate: 2020/9/1
 * @version: 1.0
 */
public class ArrayStack {

    private String[] items;
    /**
     * 栈中元素个数
     */
    private int count;
    /**
     * 栈的大小
     */
    private int size;

    public ArrayStack(int size) {
        this.items = new String[size];
        this.size = size;
        this.count = 0;
    }

    public boolean push(String item) {
        if (count == size) {
            return false;
        }
        items[count] = item;
        ++count;
        return true;
    }

    public String pop() {
        if (count == 0) {
            return null;
        }
        String tmp = items[count - 1];
        --count;
        return tmp;
    }
}











