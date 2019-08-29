package com.paranoia.jdk8.functioninterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/29 14:02
 * java.util.function.Function<T, R>接口定义了一个叫作apply的方法，它接受一个
 * 泛型T的对象，并返回一个泛型R的对象。如果你需要定义一个Lambda，将输入对象的信息映射
 * 到输出，就可以使用这个接口（比如提取苹果的重量，或把字符串映射为它的长度）。在下面的
 * 代码中，我们向你展示如何利用它来创建一个map方法，以将一个String列表映射到包含每个
 * String长度的Integer列表。
 */
public class FunctionTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("123", "asdas", "sdf3", "0");
        mapTest(list, (String s) -> s.length()).stream().forEach(ss -> System.out.println("ss = " + ss));
    }

    public static <T, R> List<R> mapTest(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<R>();
        list.forEach(one -> {
            result.add(function.apply(one));
        });

        return result;
    }
}
