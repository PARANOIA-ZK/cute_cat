package com.paranoia.webfluxreactive.collection.dbref;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ZHANGKAI
 * @date 2018/11/15
 * @description
 */
@Data
@Document(collection = "ref")
public class Ref {

    @Id
    private String id;

    private String address;

    private String father;

    private String mother;
}
