package com.paranoia.webfluxreactive.collection.transaction;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ZHANGKAI
 * @date 2018/11/16
 * @description
 */
@Data
@Document
public class T2 {

    @Id
    private String id;

    private String code;

    private String name;


    public T2(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
