package com.paranoia.mongo.service;

import com.paranoia.mongo.common.Bo;
import com.paranoia.mongo.common.Pager;
import com.paranoia.mongo.entity.SysOrganization;
import com.paranoia.mongo.entity.TestTransaction;
import com.paranoia.mongo.repository.SysOrganizationRepository;
import com.paranoia.mongo.repository.TestTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 22:29
 */
@Service
public class SysOrganizationServiceImpl implements SysOrganizationService {

    @Autowired
    private SysOrganizationRepository sysOrganizationRepository;

    @Autowired
    private TestTransactionRepository testTransactionRepository;

    /**
     * 保存  更新
     *
     * @param sysOrganization
     * @return
     */
    @Override
    public SysOrganization save(SysOrganization sysOrganization) {
        return sysOrganizationRepository.save(sysOrganization);
    }

    /**
     * 批量保存
     *
     * @param list
     */
    @Override
    public void saveAll(List<SysOrganization> list) {
        sysOrganizationRepository.saveAll(list);
    }

    /**
     * 带条件分页
     *
     * @return
     */
    @Override
    public Page<SysOrganization> pageWithCondition() {
        return null;
    }

    @Override
    public Page<SysOrganization> page(Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPageNo() - 1, pager.getPageSize());
        Page<SysOrganization> page = sysOrganizationRepository.findAll(pageable);
        return page;
    }

    /**
     * 条件查询
     * todo 待完善
     *
     * @param sysOrganization
     * @return
     */
    @Override
    public List<SysOrganization> queryWithCondition(SysOrganization sysOrganization) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                                                      .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                                                      .withIgnoreCase(true)
                                                      .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains);

        Example<SysOrganization> example = Example.of(sysOrganization, exampleMatcher);
        return sysOrganizationRepository.findAll(example);
    }

    @Override
    public Bo findOne(SysOrganization sysOrganization) {
        //ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
        //                                              .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        //                                              .withIgnoreCase(true)
        //                                              .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<SysOrganization> example = Example.of(sysOrganization);
        Optional<SysOrganization> one = sysOrganizationRepository.findOne(example);
        //Optional<SysOrganization> one = sysOrganizationRepository.findById(sysOrganization.getId());
        return one.map(this::convert).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTransaction(SysOrganization sysOrganization, TestTransaction testTransaction, TestTransaction newTest) {
        testTransactionRepository.insert(newTest);

        sysOrganizationRepository.save(sysOrganization);
        //int x = 1 / 0;
        testTransactionRepository.save(testTransaction);

    }


    private Bo convert(SysOrganization sysOrganization) {
        return new Bo(sysOrganization.getName(), sysOrganization.getId());
    }


}
























