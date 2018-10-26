package com.paranoia.webfluxreactive.service.platform;

import com.paranoia.webfluxreactive.collection.platform.SysPlatform;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

/**
 * @author ZHANGKAI
 * @date 2018/10/24
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysPlatformServiceImplTest {

    @Autowired
    private SysPlatformService sysPlatformService;

    @Test
    public void saveOrUpdate() {
        SysPlatform platform = new SysPlatform();
        platform.setCode(new ObjectId().toString().toUpperCase());
        platform.setType("系统");
        platform.setName("运营后台");
        platform.setInfo("这是一个系统类型的运营后台");
        Mono<SysPlatform> sysPlatformMono = sysPlatformService.saveOrUpdate(platform);
        System.out.println("sysPlatformMono = " + sysPlatformMono.block());
    }

    @Test
    public void saveAll() {
    }
}