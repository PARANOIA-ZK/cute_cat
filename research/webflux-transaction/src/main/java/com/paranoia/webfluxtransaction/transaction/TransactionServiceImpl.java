package com.paranoia.webfluxtransaction.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
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

    @Override
    public Mono<TransactionDocument> save(TransactionDocument transactionDocument) {
//        return transactionRepository.save(transactionDocument);
        return transactionRepository.save(transactionDocument)
                .map(zz -> {
                    if (1 == 1) {
                        throw Exceptions.propagate(new Exception("exception error"));
                    }
                    return zz;
                });
    }
}
