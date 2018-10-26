package com.paranoia.mongo.collection.account;

import lombok.Data;

/**
 * @author ZHANGKAI
 * @date 2018/10/23
 * @description 证件信息
 */
@Data
public class SysAccountCredentials {

    /**
     * 证件类型
     */
    private String type;


    /**
     * 证件号码
     */
    private String num;
}
