package com.paranoia.webfluxreactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
public interface BaseService<T> {

    /**
     * 保存或更新
     *
     * @param t
     * @return
     */
    Mono<T> saveOrUpdate(T t);

    /**
     * 批量保存或更新
     *
     * @param list
     * @return
     */
    Flux<T> saveAll(Iterable<T> list);

    /**
     * 根据DOCUMENT ENTITY 属性查询单条数据  note:实体属性查询关系为 and
     *
     * @param t
     * @return
     */
    Mono<T> findOneWithCondition(T t);

    /**
     * 根据DOCUMENT ENTITY 属性查询多条数据  note:实体属性查询关系为 and
     *
     * @param t
     * @return
     */
    Flux<T> findAllByCondition(T t);
}
