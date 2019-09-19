package com.paranoia.client.controller;

import com.paranoia.api.service.HelloService;
import com.paranoia.rsocket.client.RpcProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/9/19
 * @description
 */
@RestController
public class ConsumerController {

    @Autowired
    RpcProxy rpcProxy;

    @GetMapping("/consumer")
    public String consumerTest(String name, int age) {
        HelloService helloService = rpcProxy.create(HelloService.class);
        return helloService.sayHi(name, age);
    }

    @GetMapping("/reactive/consumer")
    public Mono<String> reactiveConsumerTest(String name, int age) {
        HelloService helloService = rpcProxy.create(HelloService.class);
        return helloService.sayHiReactive(name, age);
    }
}









