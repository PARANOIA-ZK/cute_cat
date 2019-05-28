package com.paranoia.demo.controller;

import com.paranoia.api.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PARANOIA_ZK
 * @date 2019/5/27 22:16
 */
@RestController
public class HelloController {

    @Reference
    HelloService helloService;

    @GetMapping("/hi")
    public String sayHi(@RequestParam String name) {
        return helloService.hello(name);
    }
}
