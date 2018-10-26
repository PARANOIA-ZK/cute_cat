package com.paranoia.webfluxreactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
public interface BaseService<T> {

    Mono<T> saveOrUpdate(T t);

    Flux<T> saveAll(Iterable<T> list);

    Mono<T> findOneWithCondition(T t);

}
