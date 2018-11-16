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
public class T1 {

    @Id
    private String id;

    private String code;

    private String name;

    public T1(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
