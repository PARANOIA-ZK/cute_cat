package com.paranoia.webfluxtransaction.transaction;

import com.mongodb.ClientSessionOptions;
import com.mongodb.reactivestreams.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
@Slf4j
@RestController
public class TransactionTestController {
    @Autowired
    EmbedService embedService;
    @Autowired
    CService cService;
    @Autowired
    MongoClient mongoClient;
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    private static final ClientSessionOptions SESSION_OPTIONS;

    static {
        SESSION_OPTIONS = ClientSessionOptions.builder()
                .causallyConsistent(true)
                .build();
    }

    @GetMapping("/test")
    public Mono test(@RequestParam boolean exception) {
        return Mono.from(mongoClient.startSession(SESSION_OPTIONS))
                .flatMap(session -> {
                    session.startTransaction();
                    log.info("业务进行之前的session事务状态：" + session.hasActiveTransaction());
                    return embedService.saveAC(new ADocument("张三"), new CDocument("李四"), exception)
                            .onErrorResume(e -> {
                                log.error("异常出现，controller全局回滚，此时的事务状态：" + session.hasActiveTransaction());
                                return Mono.from(session.abortTransaction()).then(Mono.error(e));
                            })
                            .flatMap(val -> Mono.from(session.commitTransaction()).then(Mono.just(val)))
                            .doFinally(signal -> session.close());
                });
    }

    @PostMapping("/test")
    public Mono testA(@RequestParam boolean exception) {
        return embedService.saveAC(new ADocument("张三"), new CDocument("李四"), exception);
    }

    @PatchMapping("/a")
    public Mono<Boolean> updateA(@RequestParam String name, @RequestParam(required = false, defaultValue = "99") int age, @RequestParam Boolean exception) {
        return Mono.from(mongoClient.startSession(SESSION_OPTIONS))
                .flatMap(session -> {
                    session.startTransaction();
                    return embedService.update(name, age, exception)
                            .onErrorResume(e -> Mono.from(session.abortTransaction()).then(Mono.error(e)))
                            .flatMap(val -> Mono.from(session.commitTransaction()).then(Mono.just(val)))
                            .doFinally(signal -> session.close());
                });
    }

    @PostMapping("/a")
    public Mono<Boolean> saveA() {
        return Mono.from(mongoClient.startSession(SESSION_OPTIONS))
                .flatMap(session -> {
                    session.startTransaction();
                    List<ADocument> collect = Stream.iterate(1, n -> n + 1)
                            .limit(2000)
                            .map(num -> {
                                ADocument aDocument = new ADocument("张三");
                                aDocument.setAge(num);
                                return aDocument;
                            })
                            .collect(Collectors.toList());
                    return embedService.saveA(collect)
                            .onErrorResume(e -> Mono.from(session.abortTransaction()).then(Mono.error(e)))
                            .flatMap(val -> Mono.from(session.commitTransaction()).then(Mono.just(val)))
                            .doFinally(signal -> session.close());
                });
    }


    /**
     * 真的鸡肋，reactive版本的事务回滚目前只能使用collection来做  springdata的repository目前暂时不支持
     * <a href="http://www.mongoing.com/%3Fp%3D6084"></a>
     * MongoDB 4.0 事务功能有一些限制，但事务资源占用超过一定阈值时，会自动 abort 来释放资源。规则包括
     * <p>
     * 1.事务的生命周期不能超过 transactionLifetimeLimitSeconds （默认60s），该配置可在线修改
     * 2.事务修改的文档数不能超过 1000 ，不可修改
     * 3.事务修改产生的 oplog 不能超过 16mb，这个主要是 MongoDB 文档大小的限制， oplog 也是一个普通的文档，也必须遵守这个约束。
     *
     * @return
     */
//    @GetMapping("/test1")
//    public Mono<Success> test1() {
//
//        MongoCollection<Document> collection = reactiveMongoTemplate.getCollection("transaction_webflux");
//        MongoCollection<Document> collection2 = reactiveMongoTemplate.getCollection("transaction_webflux_2");
//
//        ClientSessionOptions sessionOptions = ClientSessionOptions.builder()
//                .causallyConsistent(true)
//                .build();
//
//        return Mono.from(mongoClient.startSession(sessionOptions))
//                .flatMap(session -> {
//                    System.out.println("session = " + session);
//                    session.startTransaction();
//                    log.info(session.hasActiveTransaction() + "---------");
//                    return Mono.from(collection.insertOne(new TransactionDocument("123").append(null, null)))
//                            .flatMap(resultUseLess -> {
//                                return Mono.from(collection2.insertOne(new TransactionDocument2("456").append(null, null)));
//                            })
//                            .onErrorResume(e -> {
//                                System.out.println("------------------------------");
//                                return Mono.from(session.abortTransaction()).then(Mono.error(e));
//                            })
//                            .flatMap(val -> Mono.from(session.commitTransaction()).then(Mono.just(val)))
//                            .doFinally(signal -> session.close());
//                });
//
//    }
//
//    @GetMapping("/test1")
//    public Mono<List<TransactionDocument>> test1(@RequestParam String name) {
//
//        ClientSessionOptions sessionOptions = ClientSessionOptions.builder()
//                .causallyConsistent(true)
//                .build();
//        Publisher<ClientSession> sessionPublisher = mongoClient.startSession(sessionOptions);
//
//        return reactiveMongoTemplate.withSession(sessionPublisher)
//                .execute(action -> {
//                    Query query = new Query(Criteria.where("name").is(name));
//                    return action.findOne(query, TransactionDocument.class)
//                            .flatMap(result -> {
//                                return action.insert(new TransactionDocument(name + System.currentTimeMillis()));
//                            });
//                }, com.mongodb.session.ClientSession::close)
//                .collectList();
//
//    }
}
