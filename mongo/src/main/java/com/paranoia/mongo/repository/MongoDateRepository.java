package com.paranoia.mongo.repository;

import com.paranoia.mongo.collection.test.MongoDateDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author PARANOIA_ZK
 * @date 2019/3/12 22:43
 */
@Repository
public interface MongoDateRepository extends MongoRepository<MongoDateDocument,String> {
}
