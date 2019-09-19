package com.paranoia.api.service;

import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/9/18
 * @description
 */
public interface HelloService {

    String sayHi(String name, int age);

    Mono<String> sayHiReactive(String name, int age);
}
