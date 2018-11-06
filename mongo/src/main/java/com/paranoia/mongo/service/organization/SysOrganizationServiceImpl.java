package com.paranoia.mongo.service.organization;

import com.paranoia.mongo.common.Bo;
import com.paranoia.mongo.common.Pager;
import com.paranoia.mongo.collection.organization.SysOrganization;
import com.paranoia.mongo.repository.SysOrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
     * @return page
     * @apiNote :  限制为原生属性
     */
    @Override
    public Page<SysOrganization> pageWithCondition(SysOrganization sysOrganization, Pager pager) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains);

        Example<SysOrganization> example = Example.of(sysOrganization, exampleMatcher);
        Pageable pageable = PageRequest.of(pager.getPageNo() - 1, pager.getPageSize());
        return sysOrganizationRepository.findAll(example, pageable);
    }

    /**
     * 条件查询
     * todo 待完善
     *
     * @param sysOrganization
     * @return list
     */
    @Override
    public List<SysOrganization> queryWithCondition(SysOrganization sysOrganization) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains);

        Example<SysOrganization> example = Example.of(sysOrganization, exampleMatcher);

        Sort sort = Sort.by(Sort.Order.desc("create_date"));
        return sysOrganizationRepository.findAll(example, sort);
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
    public SysOrganization findOneWithCondition(SysOrganization sysOrganization) {
        Example<SysOrganization> example = Example.of(sysOrganization);
        Optional<SysOrganization> one = sysOrganizationRepository.findOne(example);
        return one.orElseThrow(RuntimeException::new);
    }


    private Bo convert(SysOrganization sysOrganization) {
        return new Bo(sysOrganization.getName(), sysOrganization.getId());
    }


}
























