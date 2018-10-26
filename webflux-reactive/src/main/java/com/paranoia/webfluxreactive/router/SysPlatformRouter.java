package com.paranoia.webfluxreactive.router;

import com.paranoia.webfluxreactive.collection.platform.SysPlatform;
import com.paranoia.webfluxreactive.handler.SysPlatformHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @author ZHANGKAI
 * @date 2018/10/24
 * @description
 */
@Configuration
public class SysPlatformRouter {

    @Autowired
    private SysPlatformHandler sysPlatformHandler;

//    @Bean
//    public RouterFunction<?> routerFunction(){
//        return RouterFunctions.route(RequestPredicates.POST("/hello"), sysPlatformHandler::getOne);
//    }
}
