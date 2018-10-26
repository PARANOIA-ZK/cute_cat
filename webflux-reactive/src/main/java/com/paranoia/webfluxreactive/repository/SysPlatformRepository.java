package com.paranoia.webfluxreactive.repository;

import com.paranoia.webfluxreactive.collection.platform.SysPlatform;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@Repository
public interface SysPlatformRepository extends ReactiveMongoRepository<SysPlatform, Object>{

    Mono<SysPlatform> findByName(String name);

}
