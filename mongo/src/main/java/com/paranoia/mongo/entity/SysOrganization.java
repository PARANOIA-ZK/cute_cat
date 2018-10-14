package com.paranoia.mongo.entity;

import lombok.Data;
import org.springframework.context.annotation.Description;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 21:39
 */
@Data
@Document(collection = "sys_organization")
@Description("机构主表")
public class SysOrganization {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String type;

    @Indexed(unique = true)
    private String appId;

    private String appSecret;

    private String salt;

    private String domain;

    private SysOrganizationDetail detail;

    private String createDate;

    private boolean isDel;

    private boolean isDisable;
}
