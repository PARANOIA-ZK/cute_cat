package com.paranoia.webfluxtransaction.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
@Service
public class EmbedServiceImpl implements EmbedService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    CService cService;

    @Override
    public Mono<Boolean> saveAC(ADocument aDocument, CDocument cDocument, boolean exception) {
        return reactiveMongoTemplate.inTransaction()
                .execute(action ->
                        action.insert(aDocument)
                                .flatMap(a -> {
                                    cDocument.setName(a.getName() + "copy");
                                    return action.insert(cDocument)
                                            .map(d -> {
                                                if (exception) {
                                                    //测试跨文档的异常回滚
                                                    throw Exceptions.propagate(new RuntimeException("模拟异常的出现"));
                                                }
                                                return d;
                                            });
                                })
                )
                .collectList()
                .map(list -> {
                    //需要注意，在execute之外的函数中产生的异常，不会触发事务的回滚。
//                    if (exception) {
//                        throw Exceptions.propagate(new RuntimeException("模拟异常的出现"));
//                    }
                    return Boolean.TRUE;
                });
    }

    @Override
    public Mono<Boolean> update(String name, int age, Boolean exception) {
        return reactiveMongoTemplate.inTransaction()
                .execute(action -> {
                            Query query = new Query(Criteria.where("name").regex(name));
                            Update update = new Update();
                            update.set("age", age);
                            return action.updateMulti(query, update, ADocument.class)
                                    .flatMap(updateResult -> {
                                        CDocument cDocument = new CDocument("花里胡哨");
                                        return action.insert(cDocument)
                                                .map(d -> {
                                                    if (exception) {
                                                        //测试跨文档的异常回滚
                                                        throw Exceptions.propagate(new RuntimeException("模拟异常的出现"));
                                                    }
                                                    return d;
                                                });
                                    });
                        }
                )
                .collectList()
                .map(list -> Boolean.TRUE);
    }

    @Override
    public Mono<Boolean> saveA(List<ADocument> list) {
        return reactiveMongoTemplate.inTransaction()
                .execute(action -> action.insertAll(list)
                )
                .collectList()
                .map(list1 -> Boolean.TRUE);
    }
}
