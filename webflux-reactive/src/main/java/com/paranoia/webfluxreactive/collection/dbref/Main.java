package com.paranoia.webfluxreactive.collection.dbref;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ZHANGKAI
 * @date 2018/11/15
 * @description
 */
@Data
@Document(collection = "main")
public class Main {

    @Id
    private String id;

    private String name;

    private String age;

    private String gender;

    @DBRef
    private Object ref;
}
