package com.paranoia.webfluxtransaction.transaction;

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
@Document(collection = "transaction_webflux")
public class TransactionDocument extends org.bson.Document {

    @Id
    private String id;

    private String code;


    @Indexed(unique = true)
    private String name;

    public TransactionDocument(String name) {
        this.name = name;
    }


    @Override
    public org.bson.Document append(String key, Object value) {
        return super.append("name", this.name);
    }
}
