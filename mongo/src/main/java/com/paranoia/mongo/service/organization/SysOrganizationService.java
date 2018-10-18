package com.paranoia.mongo.service.organization;


import com.paranoia.mongo.common.Bo;
import com.paranoia.mongo.common.Pager;
import com.paranoia.mongo.entity.TestTransaction;
import com.paranoia.mongo.entity.organization.SysOrganization;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 22:17
 */
public interface SysOrganizationService {

    SysOrganization save(SysOrganization sysOrganization);

    void saveAll(List<SysOrganization> list);

    Page<SysOrganization> pageWithCondition(SysOrganization sysOrganization, Pager pager);

    List<SysOrganization> queryWithCondition(SysOrganization sysOrganization);

    Bo findOne(SysOrganization sysOrganization);

    SysOrganization findOneWithCondition(SysOrganization sysOrganization);
}
