package com.paranoia.webfluxtransaction.transaction;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
public interface EmbedService {

    Mono<Boolean> saveAC(ADocument aDocument, CDocument cDocument, boolean exception);

    Mono<Boolean> update(String name, int age, Boolean exception);

    Mono<Boolean> saveA(List<ADocument> list);
}
