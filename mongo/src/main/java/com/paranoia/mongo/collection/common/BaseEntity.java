package com.paranoia.mongo.collection.common;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description  collection 基类
 */
@Data
public class BaseEntity implements Serializable {

    @Field("create_date")
    private Date createDate;

    @Field("is_del")
    private boolean isDel;

    @Field("is_disable")
    private boolean isDisable;

//    /**
//     * 数据版本
//     */
//    private String version = "1.0";
}
