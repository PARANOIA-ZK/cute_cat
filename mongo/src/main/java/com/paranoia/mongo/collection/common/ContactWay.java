package com.paranoia.mongo.collection.common;

import lombok.Data;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/19 10:36
 * @description : 联系方式
 */
@Data
public class ContactWay {

    private List<String> phone;

    private List<String> email;
}
