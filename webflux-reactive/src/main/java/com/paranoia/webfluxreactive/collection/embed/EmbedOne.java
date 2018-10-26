package com.paranoia.webfluxreactive.collection.embed;

import com.paranoia.webfluxreactive.collection.BaseDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

/**
 * @author ZHANGKAI
 * @date 2018/10/25
 * @description
 */
@Data
@Document(collection = "embed_one")
public class EmbedOne extends BaseDocument {

    @Id
    private String id;

    private String code;

    @Indexed(unique = true)
    private String name;

    private String type;

    @Indexed(unique = true)
    @Field("app_key")
    private String appKey;

    @Field("app_secret")
    private String appSecret;

    @Field("app_salt")
    private String appSalt;

    private EmbedTwo embedTwo;

    /**
     * 审核状态
     */
    @Field("check_status")
    private String checkStatus;

    /**
     * 机构简述
     */
    private String intro;
}
