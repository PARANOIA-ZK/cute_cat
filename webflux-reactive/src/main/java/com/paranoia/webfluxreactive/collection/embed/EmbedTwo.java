package com.paranoia.webfluxreactive.collection.embed;

import com.paranoia.webfluxreactive.collection.BaseDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/25
 * @description
 */

@Data
public class EmbedTwo extends BaseDocument {

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
    @Field("embed_three")
    private EmbedThree embedThree;

    /**
     * 机构的图片信息  注意：不是商品信息
     */
    private List<String> other;
}
