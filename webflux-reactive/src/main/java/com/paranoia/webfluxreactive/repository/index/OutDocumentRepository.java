package com.paranoia.webfluxreactive.repository.index;

import com.paranoia.webfluxreactive.collection.index.OutDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZHANGKAI
 * @date 2019/1/3
 * @description
 */
@Repository
public interface OutDocumentRepository extends ReactiveMongoRepository<OutDocument,String> {
}
