package com.paranoia.mongo.service;

import com.paranoia.mongo.common.Bo;
import com.paranoia.mongo.common.Constant;
import com.paranoia.mongo.common.NameUtil;
import com.paranoia.mongo.collection.common.ContactWay;
import com.paranoia.mongo.collection.organization.SysOrganization;
import com.paranoia.mongo.collection.organization.SysOrganizationDetail;
import com.paranoia.mongo.repository.SysOrganizationRepository;
import com.paranoia.mongo.service.organization.SysOrganizationService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 22:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysOrganizationServiceImplTest {

    @Autowired
    private SysOrganizationService sysOrganizationService;
    @Autowired
    private SysOrganizationRepository sysOrganizationRepository;

    private static List<String> province = Arrays.asList("浙江", "山东", "北京", "江苏");

    @Test
    public void save() {

        SysOrganization result = sysOrganizationService.save(getSysOrganization());

        System.out.println("result = " + result);
    }

    @Test
    public void saveAll() {

        List<SysOrganization> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(getSysOrganization());
        }
        sysOrganizationService.saveAll(list);
    }

    @Test
    public void pageWithCondition() {
    }

    @Test
    public void queryWithCondition() {
        SysOrganization sysOrganization = new SysOrganization();
        //sysOrganization.setDel(true);
        //模糊查询
        //sysOrganization.setName("公司");
//        SysOrganizationDetail detail = new SysOrganizationDetail();
//        detail.setProvince("江苏");
//        sysOrganization.setDetail(detail);
        System.out.println("--------------------");
        sysOrganizationService.queryWithCondition(sysOrganization).forEach(System.out::println);
    }

    @Test
    public void findOne() {
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setName("措脏踌苦斑科技分公司");
        Bo bo = sysOrganizationService.findOne(sysOrganization);
        System.out.println("bo = " + bo);
    }

    @Test
    public void findOneWithCondition() {
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setName("弹铃佣胀欠科技分公司");
        SysOrganization sysOrganizationss = sysOrganizationService.findOneWithCondition(sysOrganization);
        System.out.println("sysOrganizationss = " + sysOrganizationss);
    }

    @Test
    public void findOneWithConditionAndNotBackAll() {
        List<SysOrganization> sysOrganizations = sysOrganizationRepository.findByNameAndIsDelAndIsDisable("公司", false, false);
        sysOrganizations.forEach(System.out::println);
    }



    private SysOrganization getSysOrganization() {
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setCode(new ObjectId().toString().toUpperCase());
        sysOrganization.setName(NameUtil.getRandomJianHan(5) + "科技分公司");
        sysOrganization.setType(Constant.Organization.organizationType.COMPANY.getName());
        sysOrganization.setAppKey(String.valueOf(new ObjectId()));
        sysOrganization.setAppSecret(UUID.randomUUID().toString().replaceAll("-", ""));
        sysOrganization.setAppSalt("salt");

        SysOrganizationDetail sysOrganizationDetail = new SysOrganizationDetail();
        sysOrganizationDetail.setProvince(province.get(new Random().nextInt(4)));
        sysOrganizationDetail.setCity("杭州");
        sysOrganizationDetail.setAddress("xx大厦");
        ContactWay contactWay = new ContactWay();
        contactWay.setPhone(Arrays.asList("110", "726628"));
        contactWay.setEmail(Arrays.asList("726628106zxcajhsd@yeah.net"));
        sysOrganizationDetail.setContactWay(contactWay);
        sysOrganization.setDetail(sysOrganizationDetail);

        sysOrganization.setCreateDate(new Date());
        sysOrganization.setDel(false);
        sysOrganization.setDisable(false);
        return sysOrganization;
    }

}