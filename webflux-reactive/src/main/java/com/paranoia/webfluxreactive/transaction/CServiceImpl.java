package com.paranoia.webfluxreactive.transaction;

import com.mongodb.ClientSessionOptions;
import com.mongodb.TransactionOptions;
import com.mongodb.reactivestreams.client.ClientSession;
import com.mongodb.reactivestreams.client.MongoClient;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/8/27
 * @description
 */
@Slf4j
@Service
public class CServiceImpl implements CService {

    @Autowired
    MongoClient mongoClient;
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Boolean> saveC(CDocument cDocument, boolean exception) {
        ClientSessionOptions sessionOptions = ClientSessionOptions.builder()
                .causallyConsistent(true)
                .build();

        return Mono.from(mongoClient.startSession(sessionOptions))
                .flatMap(session -> {
                    session.startTransaction();
                    return reactiveMongoTemplate.inTransaction()
                            .execute(action -> action.insert(cDocument)
                                    .map(result -> {
                                        if (exception) {
                                            throw Exceptions.propagate(new RuntimeException("人工触发C-insert的失败"));
                                        }
                                        return Boolean.TRUE;
                                    })
                            )
                            .collectList()
                            .onErrorResume(e -> {
                                return Mono.from(session.abortTransaction()).then(Mono.error(e));
                            })
                            .flatMap(val -> Mono.from(session.commitTransaction()).then(Mono.just(Boolean.TRUE)))
                            .doFinally(signal -> session.close());
                });
    }
}
