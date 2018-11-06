package com.paranoia.mongo.service;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
public abstract class BaseServiceClass<T> {

    T saveOrUpdate(T t){return null;}

    List<T> saveAll(Iterable<T> list){return null;}
}
