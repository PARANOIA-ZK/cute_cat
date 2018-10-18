package com.paranoia.mongo.entity.organization;

import com.paranoia.mongo.entity.common.ContactWay;
import com.paranoia.mongo.entity.common.Images;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 21:36
 */
@Data
public class SysOrganizationDetail {

    private String province;

    private String city;

    private String district;

    private String address;

    /**
     * 细化类型
     */
    private String type;

    private String level;

    /**
     * 标签
     */
    private String tag;

    /**
     * 联系方式
     */
    @Field("contact_way")
    private ContactWay contactWay;

    /**
     * 机构的图片信息  注意：不是商品信息
     */
    private List<Images> images;


}
