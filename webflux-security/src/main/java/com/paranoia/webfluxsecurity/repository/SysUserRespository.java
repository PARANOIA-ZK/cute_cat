package com.paranoia.webfluxsecurity.repository;

import com.paranoia.webfluxsecurity.collection.SysUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/28 14:40
 */
@Repository
public interface SysUserRespository extends ReactiveMongoRepository<SysUser, String> {

    Mono<SysUser> findByUserName(String userName);
}
