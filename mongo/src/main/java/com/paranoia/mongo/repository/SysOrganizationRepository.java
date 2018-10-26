package com.paranoia.mongo.repository;

import com.paranoia.mongo.collection.organization.SysOrganization;
import org.springframework.context.annotation.Description;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
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

    @Query(value = "{" +
            "name:{$regex:?0}," +
            "is_del:?1," +
            "is_disable:?2}", fields = "{detail:1}")
    @Description("https://docs.mongodb.com/manual/reference/operator/query/regex/")
    List<SysOrganization> findByNameAndIsDelAndIsDisable(String name, boolean isDel, boolean isDisable);

}
