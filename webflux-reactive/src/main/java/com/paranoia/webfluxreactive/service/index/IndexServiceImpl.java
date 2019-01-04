package com.paranoia.webfluxreactive.service.index;

import com.paranoia.webfluxreactive.collection.index.OutDocument;
import com.paranoia.webfluxreactive.repository.index.OutDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/1/3
 * @description
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private OutDocumentRepository outDocumentRepository;

    @Override
    public Mono<OutDocument> saveOrUpdate(OutDocument outDocument) {
        return outDocumentRepository.save(outDocument);
    }

    @Override
    public Flux<OutDocument> saveAll(Iterable<OutDocument> list) {
        return null;
    }

    @Override
    public Mono<OutDocument> findOneWithCondition(OutDocument outDocument) {
        return null;
    }

    @Override
    public Flux<OutDocument> findAllByCondition(OutDocument outDocument) {
        return null;
    }
}
