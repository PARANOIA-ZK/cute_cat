package com.paranoia.demo.service;

import com.paranoia.api.HelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author PARANOIA_ZK
 * @date 2019/5/27 21:58
 */
@Service
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hi , how are you ? my friend " + name;
    }
}
