package com.paranoia.mongo.collection.account;

import com.paranoia.mongo.collection.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "sys_account")
public class SysAccount extends BaseEntity {

    @Id
    private String id;

    private String code;

    @Field("organization_code")
    private String organizationCode;

    @Field("organization_name")
    private String organizationName;

    /**
     * 该账号注册过的平台列表
     */
    @Field("registered_platform")
    private List<SysAccountRegisteredPlatform> registeredPlatform;

    @Indexed(unique = true)
    private String account;

    private String password;

    private String salt;

    private String name;

    private String gender;

    private String phone;

    private String email;

    private List<SysAccountCredentials> credentials;

    private List<String> address;

    /**
     * 联系人
     */
    private List<SysAccountContacts> contacts;

}










































