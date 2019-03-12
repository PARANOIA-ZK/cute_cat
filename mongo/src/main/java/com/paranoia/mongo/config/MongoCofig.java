//package com.paranoia.mongo.config;
//
//import com.mongodb.MongoClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.MongoTransactionManager;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//
///**
// * @author PARANOIA_ZK
// * @date 2018/10/15 17:41
// */
//@Configuration
//public class MongoCofig extends AbstractMongoConfiguration{
//    @Override
//    public MongoClient mongoClient() {
//        return new MongoClient("47.106.221.253",27018);
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return "mine";
//    }
//
//    @Bean
//    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
//        return new MongoTransactionManager(dbFactory);
//    }
//}
