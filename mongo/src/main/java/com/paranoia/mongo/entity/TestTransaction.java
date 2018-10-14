package com.paranoia.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/15 18:02
 */
@Data
@Document(collection = "test_transaction")
public class TestTransaction {

    @Id
    private String id;

    private String name;

    public TestTransaction(String name) {
        this.name = name;
    }
}
