package com.paranoia.mongo.service.platform;

import com.paranoia.mongo.collection.platform.SysPlatformRole;
import com.paranoia.mongo.repository.SysPlatformRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/23
 * @description
 */
@Service
public class SysPlatformRoleServiceImpl implements SysPlatformRoleService {

    @Autowired
    private SysPlatformRoleRepository sysPlatformRoleRepository;


    @Override
    public SysPlatformRole saveOrUpdate(SysPlatformRole sysPlatformRole) {
        return null;
    }

    @Override
    public List<SysPlatformRole> saveAll(Iterable<SysPlatformRole> list) {
        return null;
    }
}
