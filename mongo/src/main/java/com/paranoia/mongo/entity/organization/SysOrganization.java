package com.paranoia.mongo.entity.organization;

import com.paranoia.mongo.entity.common.BaseEntity;
import lombok.Data;
import org.springframework.context.annotation.Description;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 21:39
 */
@Data
@Document(collection = "sys_organization")
@Description("机构表")
public class SysOrganization extends BaseEntity {

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

    private SysOrganizationDetail detail;

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
