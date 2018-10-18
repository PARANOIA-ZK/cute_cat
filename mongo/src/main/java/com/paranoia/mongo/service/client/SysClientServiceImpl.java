package com.paranoia.mongo.service.client;

import com.paranoia.mongo.entity.client.SysClient;
import com.paranoia.mongo.repository.SysClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/23
 * @description
 */
@Service
public class SysClientServiceImpl implements SysClientService {

    @Autowired
    private SysClientRepository sysClientRepository;

    @Override
    public SysClient saveOrUpdate(SysClient sysClient) {
        return null;
    }

    @Override
    public List<SysClient> saveAll(Iterable<SysClient> list) {
        return null;
    }
}
