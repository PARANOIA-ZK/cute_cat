package com.paranoia.webfluxreactive.handler;

import com.paranoia.webfluxreactive.collection.platform.SysPlatform;
import com.paranoia.webfluxreactive.service.platform.SysPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2018/10/24
 * @description
 */
@Component
public class SysPlatformHandler {

    @Autowired
    private SysPlatformService sysPlatformService;

    public Mono<ServerResponse> getOne(SysPlatform sysPlatform){
        Mono<SysPlatform> oneWithCondition = sysPlatformService.findOneWithCondition(sysPlatform);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body((BodyInserter<?, ? super ServerHttpResponse>) oneWithCondition);
    }
}
