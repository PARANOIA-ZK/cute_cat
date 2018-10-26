package com.paranoia.mongo.collection.client;

import com.paranoia.mongo.collection.account.SysAccountCredentials;
import com.paranoia.mongo.collection.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/23
 * @description 客户
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "sys_client")
public class SysClient extends BaseEntity {

    @Id
    private String id;

    private String code;

    @Field("account_list")
    private List<String> accountList;

    /**
     * 客户来源
     */
    private String source;

    private String name;

    private String gender;

    private String phone;

    private String email;

    private List<SysAccountCredentials> credentials;

    private List<String> address;

    @Field("birth_date")
    private String birthDate;

    @Field("marital_status")
    private String maritalStatus;
}
