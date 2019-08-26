package com.paranoia.webfluxtransaction.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<TransactionDocument> save(TransactionDocument transactionDocument,boolean exception) {
//        return transactionRepository.save(transactionDocument);
//        return transactionRepository.save(transactionDocument)
//                .map(zz -> {
//                    if (exception) {
//                        throw Exceptions.propagate(new RuntimeException("模拟异常的出现"));
//                    }
//                    return zz;
//                });
        return reactiveMongoTemplate.inTransaction()
                .execute(action -> action.insert(transactionDocument)
                          .map(result->{
                              if (exception){
                                  throw Exceptions.propagate(new RuntimeException("模拟异常的出现"));
                              }
                              return result;
                          }));
    }
}
