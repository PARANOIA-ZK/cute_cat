package com.paranoia.jdk8.optional;

import java.util.Optional;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/20 10:57
 * @description optional工具类
 */
public class OptionalUtil {


    /**
     * @param string
     * @return
     * @apiNote string to int  // 这里不要使用optionalint 这种基础类型  ->  不支持map  flatmap filter 这些方法
     */
    public static Optional<Integer> stringToInt(String string) {
        try {
            // 预防出现的转换异常 捕捉并且做好补救方案
            return Optional.of(Integer.parseInt(string));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
