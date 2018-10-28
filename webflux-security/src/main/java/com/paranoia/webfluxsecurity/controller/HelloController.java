package com.paranoia.webfluxsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/28 14:16
 */
@RestController
@RequestMapping("/hi")
public class HelloController {

    @GetMapping()
    public String sayHi(){
        return "你好啊";
    }
}
