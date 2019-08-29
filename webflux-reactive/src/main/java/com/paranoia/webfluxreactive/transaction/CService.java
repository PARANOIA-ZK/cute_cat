package com.paranoia.webfluxreactive.transaction;

import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
public interface CService {


    Mono<Boolean> saveC(CDocument cDocument, boolean exception);
}
