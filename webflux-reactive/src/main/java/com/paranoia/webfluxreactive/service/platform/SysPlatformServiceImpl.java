package com.paranoia.webfluxreactive.service.platform;

import com.paranoia.webfluxreactive.collection.platform.SysPlatform;
import com.paranoia.webfluxreactive.repository.SysPlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@Service
public class SysPlatformServiceImpl implements SysPlatformService {

    @Autowired
    private SysPlatformRepository sysPlatformRepository;


    @Override
    public Mono<SysPlatform> saveOrUpdate(SysPlatform sysPlatform) {
        return sysPlatformRepository.save(sysPlatform);
    }

    @Override
    public Flux<SysPlatform> saveAll(Iterable<SysPlatform> list) {
        return null;
    }

    @Override
    public Mono<SysPlatform> findOneWithCondition(SysPlatform sysPlatform) {
        Example<SysPlatform> example = Example.of(sysPlatform);
        return sysPlatformRepository.findOne(example);
    }

    @Override
    public Flux<SysPlatform> findAllByCondition(SysPlatform sysPlatform) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                                                      .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                                                      .withIgnoreCase(true)
                                                      .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<SysPlatform> example = Example.of(sysPlatform, exampleMatcher);
        return sysPlatformRepository.findAll(example);
    }
}
