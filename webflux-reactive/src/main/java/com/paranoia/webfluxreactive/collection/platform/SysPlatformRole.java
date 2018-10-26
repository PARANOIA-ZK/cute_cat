package com.paranoia.webfluxreactive.collection.platform;

import com.paranoia.webfluxreactive.collection.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "sys_platform_role")
public class SysPlatformRole extends BaseDocument {

    @Id
    private String id;

    @Field("role_code")
    private String roleCode;

    /**
     * 机构编码
     */
    @Field("organization_code")
    private String organizationCode;

    /**
     * 平台编码
     */
    @Field("platform_code")
    private String platformCode;


    //todo
    private String resource;


}
