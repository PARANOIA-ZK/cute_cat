package com.paranoia.webfluxreactive.service.platform;


import com.paranoia.webfluxreactive.collection.embed.EmbedArray;
import com.paranoia.webfluxreactive.collection.embed.EmbedOne;
import com.paranoia.webfluxreactive.collection.embed.EmbedThree;
import com.paranoia.webfluxreactive.collection.embed.EmbedTwo;
import com.paranoia.webfluxreactive.common.NameUtil;
import com.paranoia.webfluxreactive.repository.EmbedArrayRepository;
import com.paranoia.webfluxreactive.repository.EmbedOneRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @author ZHANGKAI
 * @date 2018/10/24
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmbedOneTest {

    @Autowired
    private EmbedOneRepository embedOneRepository;
    @Autowired
    EmbedArrayRepository embedArrayRepository;

    private static List<String> province = Arrays.asList("浙江", "山东", "北京", "江苏");
    private static List<String> city = Arrays.asList("杭州", "青岛", "成都", "济南", "南京");
    private static List<String> name = Arrays.asList("张三", "李四", "王五", "托马斯", "MLXG");

    //@Test
    //public void saveOrUpdate() {
    //    EmbedOne embedOne = getEmbedOne();
    //
    //    Mono<EmbedOne> save = embedOneRepository.save(embedOne);
    //    System.out.println("save = " + save.block());
    //}
    //
    //@Test
    //public void saveList() {
    //    int num = 100_000;
    //    List<EmbedOne> embedOneList = new ArrayList<>(150_000);
    //    for (int i = 0; i < num; i++) {
    //        embedOneList.add(getEmbedOne());
    //    }
    //    System.out.println("embedOneList = " + embedOneList.size());
    //    Flux<EmbedOne> embedOneFlux = embedOneRepository.saveAll(embedOneList);
    //    System.out.println("embedOneFlux = " + embedOneFlux.count().block());
    //}

    @Test
    public void saveEmbedList() {
        int num = 10_000;
        List<EmbedArray> embedOneList = new ArrayList<>(150_000);
        for (int i = 0; i < num; i++) {
            embedOneList.add(getEmbedArray());
        }
        System.out.println("embedOneList = " + embedOneList.size());
        Flux<EmbedArray> embedOneFlux = embedArrayRepository.saveAll(embedOneList);
        System.out.println("embedOneFlux = " + embedOneFlux.count().block());
    }

    @Test
    public void  findByName() {

        Mono<EmbedArray> byName = embedArrayRepository.findFirstByName("123qwe");
        System.out.println("embedOneFlux = " + byName.block());
    }

    private EmbedArray getEmbedArray() {

        EmbedArray embedArray = new EmbedArray();
        embedArray.setName(NameUtil.getRandomJianHan(5));
        embedArray.setCode(NameUtil.getRandomJianHan(3));


        List<EmbedTwo> addList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            EmbedTwo embedTwo = new EmbedTwo();
            embedTwo.setProvince(province.get(new Random().nextInt(4)));
            embedTwo.setCity(city.get(new Random().nextInt(5)));

            EmbedThree embedThree = new EmbedThree();
            embedThree.setCode(new ObjectId().toString().toUpperCase());
            embedThree.setName(name.get(new Random().nextInt(5)));
            embedThree.setBirthDate(new Date());

            embedTwo.setEmbedThree(embedThree);
            addList.add(embedTwo);
        }


        embedArray.setEmbedTwos(addList);
        return embedArray;
    }

    private EmbedOne getEmbedOne() {
        EmbedOne embedOne = new EmbedOne();
        embedOne.setCode(new ObjectId().toString().toUpperCase());
        embedOne.setName(NameUtil.getRandomJianHan(5));
        embedOne.setType(NameUtil.getRandomJianHan(3));
        embedOne.setAppKey(UUID.randomUUID().toString().replaceAll("-", ""));
        embedOne.setAppSecret(UUID.randomUUID().toString().replaceAll("-", ""));
        embedOne.setAppSalt(UUID.randomUUID().toString().replaceAll("-", ""));

        EmbedTwo embedTwo = new EmbedTwo();
        embedTwo.setProvince(province.get(new Random().nextInt(4)));
        embedTwo.setCity(city.get(new Random().nextInt(5)));

        EmbedThree embedThree = new EmbedThree();
        embedThree.setCode(new ObjectId().toString().toUpperCase());
        embedThree.setName(name.get(new Random().nextInt(5)));
        embedThree.setBirthDate(new Date());

        embedTwo.setEmbedThree(embedThree);

        embedOne.setEmbedTwo(embedTwo);
        return embedOne;
    }

    @Test
    public void saveAll() {
    }
}