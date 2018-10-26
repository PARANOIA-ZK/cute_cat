package com.paranoia.mongo.repository;

import com.paranoia.mongo.collection.platform.SysPlatform;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@Repository
public interface SysPlatformRepository extends MongoRepository<SysPlatform, Object> {

}
