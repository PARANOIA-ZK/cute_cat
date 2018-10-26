package com.paranoia.webfluxreactive.repository;

import com.paranoia.webfluxreactive.collection.embed.EmbedOne;
import lombok.Data;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHANGKAI
 * @date 2018/10/25
 * @description
 */
@Repository
public interface EmbedOneRepository extends ReactiveMongoRepository<EmbedOne,String> {
}
