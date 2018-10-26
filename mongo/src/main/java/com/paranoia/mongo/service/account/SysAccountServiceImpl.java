package com.paranoia.mongo.service.account;

import com.paranoia.mongo.collection.account.SysAccount;
import com.paranoia.mongo.repository.SysAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2018/10/23
 * @description
 */
@Service
public class SysAccountServiceImpl implements SysAccountService {

    @Autowired
    private SysAccountRepository sysAccountRepository;

    @Override
    public SysAccount saveOrUpdate(SysAccount sysAccount) {
        return null;
    }

    @Override
    public List<SysAccount> saveAll(Iterable<SysAccount> list) {
        return null;
    }
}
