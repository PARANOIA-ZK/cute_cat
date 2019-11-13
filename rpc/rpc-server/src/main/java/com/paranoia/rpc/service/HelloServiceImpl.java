package com.paranoia.rpc.service;

import com.paranoia.api.service.HelloService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ZHANGKAI
 * @date 2019/9/18
 * @description
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String name, int age) {
        return "你好," + name + ",你的年龄是：" + age;
    }

    @Override
    public Mono<String> sayHiReactive(String name, int age) {
        if (age == 2) {
            throw new RuntimeException("测试异常");
        }
        return Mono.just("reactive mono //你好," + name + ",你的年龄是：" + age);
    }

    @Override
    public Flux<Integer> fluxRequest(int num) {
        return Flux.fromStream(new Random().ints(num).boxed());
    }
}
