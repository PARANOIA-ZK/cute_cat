package com.paranoia.webfluxtransaction.transaction;

import com.mongodb.ClientSessionOptions;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.Success;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
@Slf4j
@RestController
public class TransactionTestController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    MongoClient mongoClient;

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/test")
    public Mono test(@RequestParam boolean exception) {


        ClientSessionOptions sessionOptions = ClientSessionOptions.builder()
                .causallyConsistent(true)
                .build();

        return Mono.from(mongoClient.startSession(sessionOptions))
                .flatMap(session -> {
                    session.startTransaction();
                    log.info(session.hasActiveTransaction() + "");
                    return transactionService.save(new TransactionDocument("张三123"), exception)
                            .onErrorResume(e -> {
                                System.out.println("------------------------------");
                                return Mono.from(session.abortTransaction()).then(Mono.error(e));
                            })
                            .collectList()
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
