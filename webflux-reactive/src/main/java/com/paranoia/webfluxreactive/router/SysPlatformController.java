package com.paranoia.webfluxreactive.router;

import com.paranoia.webfluxreactive.collection.platform.SysPlatform;
import com.paranoia.webfluxreactive.service.platform.SysPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2018/10/25
 * @description
 */
@RestController
@RequestMapping("/platform")
@Slf4j
public class SysPlatformController {

    @Autowired
    private SysPlatformService sysPlatformService;

    @GetMapping()
    public Mono<SysPlatform> getOne(SysPlatform sysPlatform) {
        Mono<SysPlatform> oneWithCondition = sysPlatformService.findOneWithCondition(sysPlatform);
        return oneWithCondition;
    }

    @PostMapping()
    public Mono<SysPlatform> saveOne(@RequestBody SysPlatform sysPlatform) {
        Mono<SysPlatform> oneWithCondition = sysPlatformService.saveOrUpdate(sysPlatform);
        return oneWithCondition;
    }


//    @PostMapping()
//    public Mono<ServerResponse> saveOne(@RequestBody SysPlatform sysPlatform) {
//        Mono<SysPlatform> oneWithCondition = sysPlatformService.saveOrUpdate(sysPlatform);
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .body(oneWithCondition, SysPlatform.class);
//    }

}
