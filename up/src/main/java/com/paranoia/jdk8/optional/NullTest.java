package com.paranoia.jdk8.optional;

import java.util.Optional;
import java.util.Properties;

/**
 * @author PARANOIA_ZK
 * @date 2018/8/16 10:23
 */
public class NullTest {
    public static void main(String[] args) throws Exception {

        Car car = new Car();
        car.setName("雪佛兰");

        Optional<Car> optionalCar = Optional.ofNullable(car);

        //使用map 从optional中提取和转换值
        Optional<String> name = optionalCar.map(Car::getName);

        //有值的话就会打印  但是如何解决  空字符串问题？
        name.ifPresent(value -> System.out.println("value = " + value));

        String ss = name.orElse("未知");
        System.out.println("ss = " + ss);

        name.orElseThrow(() -> new RuntimeException("失败"));


        //-------------------------------
        Properties properties = new Properties();
        properties.setProperty("a", "-1");
        properties.setProperty("b", "2");
        properties.setProperty("c", "-3");

        int result = getProperty(properties, "b");
        System.out.println("result = " + result);
    }


    private static int getProperty(Properties properties, String name) {
        return Optional.ofNullable(properties.getProperty(name))  //这里是预防传进来一个不存下的key-name
                       .flatMap(OptionalUtil::stringToInt)
                       .filter(i -> i > 0)
                       .orElse(0);
    }
}
