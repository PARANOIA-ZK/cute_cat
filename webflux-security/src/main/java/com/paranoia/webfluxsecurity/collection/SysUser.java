package com.paranoia.webfluxsecurity.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/28 14:38
 */
@Data
@Document(collection = "sys_user")
public class SysUser {

    @Id
    public String id;

    @Indexed(unique = true)
    @Field("user_name")
    public String userName;

    @Field("password")
    public String passWord;

    public String role;
}
