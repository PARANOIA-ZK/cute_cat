package com.paranoia.webfluxsecurity.service;

import com.paranoia.webfluxsecurity.collection.SysUser;
import com.paranoia.webfluxsecurity.repository.SysUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/28 14:41
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserRespository sysUserRespository;


    public Mono<SysUser> save(SysUser sysUser) {
        return sysUserRespository.save(sysUser);
    }

    public Mono<SysUser> findUserByUserName(String name) {
        return sysUserRespository.findByUserName(name);
    }
}
