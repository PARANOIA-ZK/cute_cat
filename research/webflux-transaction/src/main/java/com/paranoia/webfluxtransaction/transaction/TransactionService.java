package com.paranoia.webfluxtransaction.transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
public interface TransactionService {

    Flux<TransactionDocument> save(TransactionDocument transactionDocument, boolean exception);
}
