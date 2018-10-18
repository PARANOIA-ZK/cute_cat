package com.paranoia.mongo.service;

import com.paranoia.mongo.entity.label.Label;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/19 16:27
 */
public interface LabelService {

    Label saveOrUpdate(Label label);

    /**
     * 子节点查父节点
     *
     * @param value
     * @return
     */
    Label getParentByChildren(String value);


    /**
     * 父节点查子节点
     *
     * @param path
     * @return
     */
    List<Label> getChildrenByParent(String path);
}
