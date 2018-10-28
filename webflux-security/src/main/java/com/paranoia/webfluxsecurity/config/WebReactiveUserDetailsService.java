package com.paranoia.webfluxsecurity.config;

import com.paranoia.webfluxsecurity.collection.SysUser;
import com.paranoia.webfluxsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/28 14:50
 */
@Service
public class WebReactiveUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<SysUser> sysUserMono = sysUserService.findUserByUserName(username);
        SysUser sysUser = sysUserMono.block();
        System.out.println("sysUser = " + sysUser);
        UserDetails userDetails = User.withUsername(sysUser.getUserName())
                                      .password(sysUser.getPassWord())
                                      .roles(sysUser.getRole())
                                      .build();
        return Mono.just(userDetails);
    }
}

