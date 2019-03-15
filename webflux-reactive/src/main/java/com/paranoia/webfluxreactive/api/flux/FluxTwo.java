package com.paranoia.webfluxreactive.api.flux;

import reactor.core.publisher.Flux;

/**
 * @author ZHANGKAI
 * @date 2019/3/8
 * @description
 */
public class FluxTwo {

    public static void main(String[] args) {
        handle();
    }


    /**
     * 映射 + 过滤   map + filter
     */
    private static void handle() {
        Flux<String> flux = Flux.just(-1, 54, 35, 272, 3424)
                .handle((i, sink) -> {
                    String ss = "ss";
                    if (i > 0) {
                        ss += ss + i;
                    } else {
                        ss = null;
                    }
                    if (ss != null) {
                        sink.next(ss);
                    }
                });
        flux.subscribe(System.out::println);
    }

}
