package com.paranoia.webfluxreactive.service.platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author ZHANGKAI
 * @date 2018/10/24
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZipTest {


    private Flux<String> getZipDescFlux() {
        String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
        return Flux.fromArray(desc.split("\\s+"));  // 1
    }

    @Test
    public void testSimpleOperators() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);  // 2
        Flux.zip(getZipDescFlux(), Flux.interval(Duration.ofMillis(200)))  // 3
            .subscribe(t -> System.out.println(t.getT1()), null, countDownLatch::countDown);    // 4
        countDownLatch.await(10, TimeUnit.SECONDS);     // 5
    }


    @Test
    public void testZipInMono() {
        Mono a = Mono.just("jerry");
        Mono b = Mono.just("tom");

        Mono<Tuple2> zip = a.zipWith(b);
        zip.subscribe(s -> System.out.println("s = " + s));
    }
}