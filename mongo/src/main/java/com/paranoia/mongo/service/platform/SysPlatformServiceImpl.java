package com.paranoia.mongo.service.platform;

import com.paranoia.mongo.collection.platform.SysPlatform;
import com.paranoia.mongo.repository.SysPlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public SysPlatform saveOrUpdate(SysPlatform sysPlatform) {
        return sysPlatformRepository.save(sysPlatform);
    }

    @Override
    public List<SysPlatform> saveAll(Iterable<SysPlatform> list) {
        return sysPlatformRepository.saveAll(list);
    }
}
