package com.paranoia.webfluxtransaction;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxTransactionApplication.class, args);
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://47.106.221.253:27018");
    }

}
