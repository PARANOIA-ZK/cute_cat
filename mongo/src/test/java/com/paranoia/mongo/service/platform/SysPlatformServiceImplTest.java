package com.paranoia.mongo.service.platform;

import com.paranoia.mongo.common.Constant;
import com.paranoia.mongo.collection.platform.SysPlatform;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZHANGKAI
 * @date 2018/10/22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysPlatformServiceImplTest {

    @Autowired
    private SysPlatformService sysPlatformService;

    @Test
    public void saveOrUpdate() {
        SysPlatform sysPlatform = new SysPlatform();
        sysPlatform.setCode(new ObjectId().toString().toUpperCase());
        sysPlatform.setType(Constant.Platform.platformType.SYSTEM.getName());
        sysPlatform.setName("驻点后台123");
        sysPlatform.setInfo("这是一个系统的驻点平台123");
        sysPlatformService.saveOrUpdate(sysPlatform);
    }

    @Test
    public void saveAll() {
    }
}