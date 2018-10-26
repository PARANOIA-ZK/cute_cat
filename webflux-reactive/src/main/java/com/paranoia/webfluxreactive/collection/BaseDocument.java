package com.paranoia.webfluxreactive.collection;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@Data
public class BaseDocument implements Serializable {

    @Field("create_date")
    public Date createDate;

    @Field("is_del")
    public boolean isDel;

    @Field("is_disable")
    public boolean isDisable;

}
