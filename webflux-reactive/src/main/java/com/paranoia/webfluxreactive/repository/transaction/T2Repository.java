package com.paranoia.webfluxreactive.repository.transaction;

import com.paranoia.webfluxreactive.collection.transaction.T1;
import com.paranoia.webfluxreactive.collection.transaction.T2;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHANGKAI
 * @date 2018/11/16
 * @description
 */
@Repository
public interface T2Repository extends ReactiveMongoRepository<T2,String> {
}
