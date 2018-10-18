package com.paranoia.mongo.entity.account;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/23
 * @description 联系人
 */
@Data
public class SysAccountContacts {


    private String name;

    private String gender;

    private String phone;

    private String email;

    private List<SysAccountCredentials> credentials;

    private List<String> address;

    @Field("create_date")
    private Date createDate;

    private boolean isDel;
}
