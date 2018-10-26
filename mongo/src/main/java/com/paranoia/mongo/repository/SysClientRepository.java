package com.paranoia.mongo.repository;

import com.paranoia.mongo.collection.client.SysClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHANGKAI
 * @date 2018/10/23
 * @description
 */
@Repository
public interface SysClientRepository extends MongoRepository<SysClient,Object> {


}
