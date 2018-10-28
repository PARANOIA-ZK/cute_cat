package com.paranoia.webfluxsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/28 11:37
 */
@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    //@Bean
    //public MapReactiveUserDetailsService userDetailsService() {
    //    UserDetails user = User.withDefaultPasswordEncoder()
    //                           .username("admin ")
    //                           .password("123")
    //                           .roles("ADMIN")
    //                           .build();
    //    return new MapReactiveUserDetailsService(user);
    //}

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                //.pathMatchers("/loginPage").permitAll()  //无需进行权限过滤的请求路径
                .anyExchange().authenticated()
                .and()
                .httpBasic().and()
                .formLogin()
        //.loginPage("/loginPage")  //自定义的登陆页面
        ;
        return http.build();
    }

}
