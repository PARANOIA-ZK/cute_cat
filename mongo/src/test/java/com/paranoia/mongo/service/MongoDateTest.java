package com.paranoia.mongo.service;

import com.paranoia.mongo.collection.test.MongoDateDocument;
import com.paranoia.mongo.repository.MongoDateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author PARANOIA_ZK
 * @date 2019/3/12 22:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDateTest {

    @Autowired
    MongoDateRepository mongoDateRepository;

    final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Test
    public void insert() throws ParseException {
        MongoDateDocument mongoDateDocument = new MongoDateDocument();
        Date date = sdf.get().parse("2019-03-12 11:08:12");
        mongoDateDocument.setName("tom");
        mongoDateDocument.setCreateDate(date);
        mongoDateDocument.setUpdateDate(sdf.get().format(date));
        mongoDateRepository.insert(mongoDateDocument);

        String yesterday = "2019-03-13 01:08:12";
        Date yesterdayDate = sdf.get().parse(yesterday);
        MongoDateDocument yesDoc = new MongoDateDocument();
        yesDoc.setName("tom");
        yesDoc.setCreateDate(yesterdayDate);
        yesDoc.setUpdateDate(sdf.get().format(yesterdayDate));
        mongoDateRepository.insert(yesDoc);
    }
}
