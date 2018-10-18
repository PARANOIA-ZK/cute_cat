package com.paranoia.mongo.service;

import com.paranoia.mongo.entity.label.Label;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/19 16:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelServiceImplTest {

    @Autowired
    private LabelService labelService;

    @Test
    public void saveOrUpdate() {
        Label label = new Label();
        label.setOrganizationCode(String.valueOf(new Random().nextInt(3) + 1));
        label.setValue("数码");
        label.setPath(",");
        Label label1 = labelService.saveOrUpdate(label);
        System.out.println("label1 = " + label1);
    }

    @Test
    public void getParentByChildren() {
        Label label = labelService.getParentByChildren("小米");
        System.out.println("label = " + label);
    }

    @Test
    public void getChildrenByParent() {
        labelService.getChildrenByParent("数码,").forEach(System.out::println);
    }
}