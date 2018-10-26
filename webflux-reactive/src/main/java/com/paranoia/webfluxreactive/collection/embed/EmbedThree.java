package com.paranoia.webfluxreactive.collection.embed;

import com.paranoia.webfluxreactive.collection.BaseDocument;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/25
 * @description
 */

@Data
public class EmbedThree extends BaseDocument {

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

    private List<String> address;

    @Field("birth_date")
    private Date birthDate;

    @Field("marital_status")
    private String maritalStatus;
}
