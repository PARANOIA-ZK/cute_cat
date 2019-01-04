package com.paranoia.webfluxreactive.collection.index;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

/**
 * @author ZHANGKAI
 * @date 2019/1/3
 * @description
 */
@Data
@Document(collection = "out")
@CompoundIndexes({
        @CompoundIndex(name = "inner_unique", def = "{'inner.key':1}", unique = true)
})
public class OutDocument {
    @Id
    private String id;

    private String code;

    private String name;

    private List<InnerDocument> inner;
}
