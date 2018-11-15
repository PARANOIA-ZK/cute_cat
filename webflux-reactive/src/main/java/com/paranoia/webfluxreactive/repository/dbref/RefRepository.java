package com.paranoia.webfluxreactive.repository.dbref;

import com.paranoia.webfluxreactive.collection.dbref.Main;
import com.paranoia.webfluxreactive.collection.dbref.Ref;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHANGKAI
 * @date 2018/11/15
 * @description
 */
@Repository
public interface RefRepository extends ReactiveMongoRepository<Ref,String> {
}
