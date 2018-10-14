package com.paranoia.mongo.repository;

import com.paranoia.mongo.entity.TestTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/15 18:05
 */
@Repository
public interface TestTransactionRepository extends MongoRepository<TestTransaction, String> {
}
