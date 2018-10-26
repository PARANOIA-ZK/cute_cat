package com.paranoia.webfluxreactive.collection.platform;

import com.paranoia.webfluxreactive.collection.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "sys_platform")
public class SysPlatform extends BaseDocument implements Serializable {

    @Id
    public String id;

    public String code;

    public String type;

    @Indexed(unique = true)
    public String name;

    /**
     * 平台信息
     */
    public String info;

    //todo
    public String resource;


}
