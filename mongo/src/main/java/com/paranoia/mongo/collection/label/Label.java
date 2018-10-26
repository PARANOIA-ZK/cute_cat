package com.paranoia.mongo.collection.label;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/19 15:55
 */
@Data
@NoArgsConstructor
@Document(collection = "label")
@CompoundIndexes({
        @CompoundIndex(name = "idx_uni_organization_code&value", def = "{'organization_code':1,'type':1,'value':1}", unique = true)
})
public class Label {

    @Id
    private String id;

    @Field("organization_code")
    private String organizationCode;

    private String type;

    private String value;

    private String path;

    public Label(String path) {
        this.path = path;
    }
}
