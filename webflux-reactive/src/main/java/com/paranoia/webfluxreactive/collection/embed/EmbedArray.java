package com.paranoia.webfluxreactive.collection.embed;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2019/1/18
 * @description
 */
@Data
@Document(collection = "embed_array")
public class EmbedArray {


    @Id
    private String id;


    /***/
    private String name;

    /***/
    private String code;

    /***/
    private List<EmbedTwo> embedTwos;
}
