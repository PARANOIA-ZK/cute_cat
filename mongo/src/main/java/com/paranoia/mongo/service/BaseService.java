package com.paranoia.mongo.service;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
public interface BaseService<T> {

    T saveOrUpdate(T t);

    List<T> saveAll(Iterable<T> list);
}
