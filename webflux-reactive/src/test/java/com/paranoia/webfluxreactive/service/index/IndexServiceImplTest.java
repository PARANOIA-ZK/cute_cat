package com.paranoia.webfluxreactive.service.index;

import com.paranoia.webfluxreactive.collection.index.InnerDocument;
import com.paranoia.webfluxreactive.collection.index.OutDocument;
import com.paranoia.webfluxreactive.repository.index.OutDocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2019/1/3
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexServiceImplTest {

    @Autowired
    private OutDocumentRepository outDocumentRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void saveOrUpdate() {

        OutDocument outDocument = new OutDocument();
        outDocument.setCode("QWER001");
        outDocument.setName("测试索引");

        List<InnerDocument> innerDocuments = Arrays.asList(new InnerDocument("001", "潘森"), new InnerDocument("002", "妖姬"));

        outDocument.setInner(innerDocuments);

        outDocumentRepository.save(outDocument).subscribe(System.out::println);

    }

    @Test
    public void addToInnerIndex() {

        Mono<OutDocument> documentMono = outDocumentRepository.findById("5c2de88346ce5e2a181fc29a")
                .map(result -> {
                    List<InnerDocument> inner = result.getInner();
                    inner.add(new InnerDocument("001", "潘森的爸爸2"));
                    return result;
                })
                .flatMap(newOut -> outDocumentRepository.save(newOut));

        documentMono.block();
    }

    @Test
    public void addToInnerIndexTryTemplate() {

        Query query = new Query(Criteria.where("code").is("QWER001"));
        Update update = new Update();
        update.addToSet("inner", new InnerDocument("001", "潘森的爷爷"));

        reactiveMongoTemplate.updateMulti(query, update, OutDocument.class).block();
    }
}