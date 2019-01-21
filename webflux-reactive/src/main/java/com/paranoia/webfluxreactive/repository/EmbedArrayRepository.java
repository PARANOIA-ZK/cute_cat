package com.paranoia.webfluxreactive.repository;

import com.paranoia.webfluxreactive.collection.embed.EmbedArray;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/1/18
 * @description
 */
@Repository
public interface EmbedArrayRepository extends ReactiveMongoRepository<EmbedArray,String> {

    Mono<EmbedArray> findOneByName(String name);
    Mono<EmbedArray> findFirstByName(String name);
}
