package com.paranoia.rpc;

import com.paranoia.rpc.server.RpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcServerApplication.class, args);
        try {
            new RpcServer().publish("com.paranoia.rpc.service");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Bean
//    public RpcServer getRpcServer(){
//        return new RpcServer()
//    }

}
