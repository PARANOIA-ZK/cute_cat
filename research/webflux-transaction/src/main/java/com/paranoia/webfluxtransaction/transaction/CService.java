package com.paranoia.webfluxtransaction.transaction;

import com.mongodb.reactivestreams.client.ClientSession;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2019/3/20
 * @description
 */
public interface CService {


    Mono<Boolean> saveC(CDocument cDocument, boolean exception);
}
