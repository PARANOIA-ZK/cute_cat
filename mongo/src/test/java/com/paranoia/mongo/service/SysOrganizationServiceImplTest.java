package com.paranoia.mongo.service;

import com.paranoia.mongo.common.Bo;
import com.paranoia.mongo.common.Constant;
import com.paranoia.mongo.common.Pager;
import com.paranoia.mongo.entity.SysOrganization;
import com.paranoia.mongo.entity.SysOrganizationDetail;
import com.paranoia.mongo.entity.TestTransaction;
import com.paranoia.mongo.repository.SysOrganizationRepository;
import com.paranoia.mongo.repository.TestTransactionRepository;
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
    private TestTransactionRepository testTransactionRepository;
    @Autowired
    private SysOrganizationRepository sysOrganizationRepository;

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
    public void page() {
        Pager pager = new Pager(2, 5);
        sysOrganizationService.page(pager).getContent().forEach(System.out::println);
    }

    @Test
    public void queryWithCondition() {
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setDisable(true);
        //模糊查询
        //sysOrganization.setName("61");
        sysOrganizationService.queryWithCondition(sysOrganization).forEach(System.out::println);
    }

    @Test
    public void findOne() {
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setId("5bc20b99b62e830b74c488d2");
        Bo bo = sysOrganizationService.findOne(sysOrganization);
        System.out.println("bo = " + bo);
    }

    @Test
    public void saveOne() {
        TestTransaction testTransaction = new TestTransaction("张三");
        testTransactionRepository.save(testTransaction);
    }

    @Test
    public void testTransaction() {
        Optional<TestTransaction> optionalTestTransaction = testTransactionRepository.findById("5bc46a0eb62e8325682f64b8");
        TestTransaction testTransaction = optionalTestTransaction.get();
        testTransaction.setName("张三的father");

        Optional<SysOrganization> optionalSysOrganization = sysOrganizationRepository.findById("5bc20b99b62e830b74c488d2");
        SysOrganization sysOrganization = optionalSysOrganization.get();
        sysOrganization.setName("张三的mother");

        TestTransaction newTest = new TestTransaction("张三的女儿");

        sysOrganizationService.testTransaction(sysOrganization,testTransaction,newTest);
    }


    private SysOrganization getSysOrganization() {
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setName("XX科技分公司" + new Random().nextInt());
        sysOrganization.setAppId(String.valueOf(new ObjectId()));
        sysOrganization.setAppSecret(UUID.randomUUID().toString().replaceAll("-", ""));
        sysOrganization.setSalt("salt");
        sysOrganization.setDomain("xxx.part.com");
        sysOrganization.setType(Constant.OrganizationTypeEnum.HOSPITAL.getName());
        sysOrganization.setCreateDate("");

        SysOrganizationDetail sysOrganizationDetail = new SysOrganizationDetail();
        sysOrganizationDetail.setCode(String.valueOf(new ObjectId()).toUpperCase());
        sysOrganizationDetail.setAddress("xx大厦");
        sysOrganizationDetail.setIntro("this is an introduce");
        sysOrganizationDetail.setTel("17625321349");

        sysOrganization.setDetail(sysOrganizationDetail);
        sysOrganization.setDel(false);
        sysOrganization.setDisable(false);
        return sysOrganization;
    }
}