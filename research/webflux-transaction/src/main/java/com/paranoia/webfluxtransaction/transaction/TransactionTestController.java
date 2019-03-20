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
    public Mono<TransactionDocument> test() {


        ClientSessionOptions sessionOptions = ClientSessionOptions.builder()
                .causallyConsistent(true)
                .build();

        return Mono.from(mongoClient.startSession(sessionOptions))
                .flatMap(session -> {
                    session.startTransaction();
                    log.info(session.hasActiveTransaction() + "");
                    return transactionService.save(new TransactionDocument("张三123"))
                            .onErrorResume(e -> {
                                System.out.println("------------------------------");
                                return Mono.from(session.abortTransaction()).then(Mono.error(e));
                            })
                            .flatMap(val -> Mono.from(session.commitTransaction()).then(Mono.just(val)))
                            .doFinally(signal -> session.close());
                });

    }

    /**
     * 真的鸡肋，reactive版本的事务回滚目前只能使用collection来做  springdata的repository目前暂时不支持
     * @return
     */
    @GetMapping("/test1")
    public Mono<Success> test1() {

        MongoCollection<Document> collection = reactiveMongoTemplate.getCollection("transaction_webflux");

        ClientSessionOptions sessionOptions = ClientSessionOptions.builder()
                .causallyConsistent(true)
                .build();

        return Mono.from(mongoClient.startSession(sessionOptions))
                .flatMap(session -> {
                    session.startTransaction();
                    log.info(session.hasActiveTransaction() + "");
                    return Mono.from(collection.insertOne(new TransactionDocument("123").append(null,null)))
                            .onErrorResume(e -> {
                                System.out.println("------------------------------");
                                return Mono.from(session.abortTransaction()).then(Mono.error(e));
                            })
                            .flatMap(val -> Mono.from(session.commitTransaction()).then(Mono.just(val)))
                            .doFinally(signal -> session.close());
                });

    }
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
