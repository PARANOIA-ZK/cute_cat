package com.paranoia.webfluxreactive.repository.dbref;

import com.paranoia.webfluxreactive.collection.dbref.Main;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHANGKAI
 * @date 2018/11/15
 * @description
 */
@Repository
public interface MainRepository extends ReactiveMongoRepository<Main,String> {
}
