package com.paranoia.webfluxtransaction.transaction;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
public interface TransactionRepository extends ReactiveMongoRepository<TransactionDocument,String> {
}
