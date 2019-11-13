package com.paranoia.client.controller;

import com.paranoia.api.service.HelloService;
import com.paranoia.rsocket.client.RpcProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
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

        return helloService.sayHiReactive(name, age)
                .onErrorResume(e -> Mono.just("service error ~"));
    }

    @GetMapping("/reactive/consumer/flux")
    public Flux<Integer> reactiveFluxConsumerTest(int num) {

        HelloService helloService = rpcProxy.create(HelloService.class);

        return helloService.fluxRequest(num)
//                .map(s -> (Integer) s)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Flux.just(500);
                });
    }
}









