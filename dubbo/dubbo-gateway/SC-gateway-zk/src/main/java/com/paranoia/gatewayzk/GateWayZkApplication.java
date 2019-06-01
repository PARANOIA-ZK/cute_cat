package com.paranoia.gatewayzk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author PARANOIA_ZK
 * @date 2019/5/27 19:54
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GateWayZkApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayZkApplication.class, args);
    }
}
