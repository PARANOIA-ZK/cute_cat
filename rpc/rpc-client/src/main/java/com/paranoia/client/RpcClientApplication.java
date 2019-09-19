package com.paranoia.client;

import com.paranoia.client.rpc.RpcProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcClientApplication.class, args);
    }

    @Bean
    public RpcProxy getRpcProxy(){
        return new RpcProxy();
    }

}
