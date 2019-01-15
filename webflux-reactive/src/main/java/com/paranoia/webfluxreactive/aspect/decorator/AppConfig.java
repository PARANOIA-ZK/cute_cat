package com.paranoia.webfluxreactive.aspect.decorator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.WebFilter;

/**
 * @author PARANOIA_ZK
 * @date 2019/1/9 22:14
 */
@Configuration
public class AppConfig {

    @Bean
    @Order(0)
    public WebFilter webFilter() {
        return (exchange, chain) -> chain.filter(new PayloadServerWebExchangeDecorator(exchange));
    }
}
