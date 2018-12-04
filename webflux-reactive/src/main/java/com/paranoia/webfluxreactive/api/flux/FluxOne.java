package com.paranoia.webfluxreactive.api.flux;

import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author PARANOIA_ZK
 * @date 2018/11/21 20:16
 * @description : Flux api  的测试demo
 */
public class FluxOne {

    public static void main(String[] args) {
        //testRange();
        //testSubscribe();
        //testHandle();
        testSchedules();
    }

    private static void testRange() {
        Flux<Integer> range = Flux.range(5, 3);
        range.subscribe(System.out::println);
    }

    private static void testSubscribe() {
        Flux<Integer> range = Flux.range(1, 5)
                                  .map(i -> {
                                      if (i <= 3) {
                                          return i;
                                      }
                                      throw new RuntimeException("error:" + i);
                                  });
        range.subscribe(System.out::println, System.err::println, () -> System.out.println("DONE"));
    }

    private static void testHandle() {
        Flux<String> stringFlux = Flux.just("", "hi", "hello")
                                      .handle((i, sink) -> {
                                          if (!StringUtils.isEmpty(i)) {
                                              sink.next(i);
                                          }
                                      });

        stringFlux.subscribe(System.out::println);
    }

    private static void testSchedules() {
        Flux.range(0, 10)
            .subscribeOn(Schedulers.newSingle("single"))
            .subscribe(System.out::println);
    }


}


















