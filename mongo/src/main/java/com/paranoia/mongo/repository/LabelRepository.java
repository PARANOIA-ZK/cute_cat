package com.paranoia.mongo.repository;

import com.paranoia.mongo.collection.label.Label;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/19 16:26
 */
@Repository
public interface LabelRepository extends MongoRepository<Label, Object> {

    Label findByValue(String value);
}
