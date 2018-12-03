package com.paranoia.webfluxreactive.service.platform;

import com.paranoia.webfluxreactive.collection.dbref.Main;
import com.paranoia.webfluxreactive.collection.dbref.Ref;
import com.paranoia.webfluxreactive.repository.dbref.MainRepository;
import com.paranoia.webfluxreactive.repository.dbref.RefRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2018/11/15
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DBrefTest {

    @Autowired
    private MainRepository mainRepository;

    @Autowired
    private RefRepository refRepository;

    @Test
    public void testInsert() {

        Ref ref = new Ref();
        ref.setAddress("滨江火炬大道");
        ref.setFather("zhang");
        ref.setMother("wang");
        Mono<Ref> save = refRepository.save(ref);

        Main main = new Main();
        main.setName("main");
        main.setAge("18");
        main.setGender("男");
        main.setRef(save.block());
        mainRepository.save(main).subscribe();
    }

    @Test
    public void getMain() {
        Mono<Main> byId = mainRepository.findById("5bed0be146ce5e0524feb202");
        Main main = byId.block();
        System.out.println("main = " + main);
    }
}
