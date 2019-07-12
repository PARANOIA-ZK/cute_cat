package com.paranoia.webfluxreactive.api.flux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.util.List;

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
        testCollectList();
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

    private static void testTupleUtils() {
        Flux.just("one")
                .zipWith(Mono.just("two"))
                .map(tuple -> {
                    String t1 = tuple.getT1();
                    String t2 = tuple.getT2();
                    return new User(t1, t2);
                });
    }

    private static void testCollectList() {
        Flux.range(0, 10)
                .map(a->{
                    System.out.println("a = " + a);
                    return a;
                })
                .collectList()
                .map(aa->{
                    aa.remove(2);
                    return aa;
                })
                .subscribe(System.out::println);
    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    private String name;
    private String age;
}


















