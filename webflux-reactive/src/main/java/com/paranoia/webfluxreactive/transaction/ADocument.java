package com.paranoia.webfluxreactive.transaction;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
@Data
@Document(collection = "a_document")
public class ADocument {

    @Id
    private String id;

    private String code;

    private String name;

    /***/
    private int age;

    public ADocument(String name) {
        this.name = name;
    }


//    @Override
//    public org.bson.Document append(String key, Object value) {
//        return super.append("name", this.name);
//    }
}
