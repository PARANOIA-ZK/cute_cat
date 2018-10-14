package com.paranoia.mongo.repository;

import com.paranoia.mongo.entity.SysOrganization;
import org.springframework.context.annotation.Description;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 22:14
 */
@Repository
@Description("https://docs.spring.io/spring-data/data-mongodb/docs/current/reference/html/index.html")
public interface SysOrganizationRepository extends MongoRepository<SysOrganization, Object> {

    @Deprecated
    List<SysOrganization> findAllByType(String type);


    @Description("这种可以通过条件查询的实现，获取集合之后使用size， but! 建议还是不要这么做 ！")
    long countByTypeAndDisable(String type, boolean isDisable);
}
