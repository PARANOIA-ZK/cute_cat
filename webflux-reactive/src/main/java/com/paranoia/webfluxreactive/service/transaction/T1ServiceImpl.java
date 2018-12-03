package com.paranoia.webfluxreactive.service.transaction;

import com.paranoia.webfluxreactive.collection.transaction.T1;
import com.paranoia.webfluxreactive.collection.transaction.T2;
import com.paranoia.webfluxreactive.repository.transaction.T1Repository;
import com.paranoia.webfluxreactive.repository.transaction.T2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2018/11/16
 * @description
 */
@Service
public class T1ServiceImpl {

    @Autowired
    private T1Repository t1Repository;

    @Autowired
    private T2Repository t2Repository;

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;


    @Transactional
    public Mono save(T1 t1, T2 t2){

        //reactiveMongoTemplate.inTransaction()

        t1Repository.save(t1).subscribe();
        int a = 1/0;
        t2Repository.save(t2).subscribe();
        return Mono.empty();
    }
}
