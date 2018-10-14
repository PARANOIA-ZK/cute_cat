package com.paranoia.mongo.common;

import lombok.Getter;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 22:53
 */
public interface Constant {

    @Getter
    enum OrganizationTypeEnum {
        HOSPITAL("医院"),
        OTHER("其他巴拉巴拉...");

        private String name;

        OrganizationTypeEnum(String name) {
            this.name = name;
        }
    }
}
