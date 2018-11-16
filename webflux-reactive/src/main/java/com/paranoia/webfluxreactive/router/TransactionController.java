package com.paranoia.webfluxreactive.router;

import com.paranoia.webfluxreactive.collection.transaction.T1;
import com.paranoia.webfluxreactive.collection.transaction.T2;
import com.paranoia.webfluxreactive.service.transaction.T1ServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2018/11/16
 * @description
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private T1ServiceImpl t1Service;

    @GetMapping("/save")
    public Mono save() {

        System.out.println("t1Service = " + t1Service);

        T1 t1 = new T1(new ObjectId().toString().toUpperCase(), "T1");

        T2 t2 = new T2(new ObjectId().toString().toUpperCase(), "T2");

        t1Service.save(t1, t2).subscribe();


        return Mono.empty();
    }
}
