package com.paranoia.mongo.entity.account;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author ZHANGKAI
 * @date 2018/10/23
 * @description
 */
@Data
public class SysAccountRegisteredPlatform {

    @Field("platform_type")
    private String platformType;

    @Field("platform_code")
    private String platformCode;

    @Field("role_code")
    private String roleCode;

    /**
     * 是否在此平台禁用
     */
    private boolean isDisable;
}
