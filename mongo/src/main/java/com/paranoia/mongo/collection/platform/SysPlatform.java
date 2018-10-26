package com.paranoia.mongo.collection.platform;

import com.paranoia.mongo.collection.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "sys_platform")
public class SysPlatform extends BaseEntity {

    @Id
    private String id;

    private String code;

    private String type;

    @Indexed(unique = true)
    private String name;

    /**
     * 平台信息
     */
    private String info;

    //todo
    private String resource;


}
