package com.paranoia.activity.resttemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ZHANGKAI
 * @date 2019/2/22
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/template")
    public void getResponse(){
        System.out.println("restTemplate = " + restTemplate);
    }
}
