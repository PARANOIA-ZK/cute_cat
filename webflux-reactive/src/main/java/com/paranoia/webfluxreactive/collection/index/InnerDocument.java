package com.paranoia.webfluxreactive.collection.index;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZHANGKAI
 * @date 2019/1/3
 * @description
 */
@Data
@AllArgsConstructor
public class InnerDocument {

    private String key;

    private String value;
}
