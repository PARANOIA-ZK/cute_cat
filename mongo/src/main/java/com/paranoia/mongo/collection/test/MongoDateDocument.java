package com.paranoia.mongo.collection.test;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author PARANOIA_ZK
 * @date 2019/3/12 22:41
 */
@Data
@Document("test_mongo_date")
public class MongoDateDocument {

    @Id
    private String id ;

    private String name ;

    @Field("create_date")
    private Date createDate; ;

    @Field("update_date")
    private String updateDate ;


}
