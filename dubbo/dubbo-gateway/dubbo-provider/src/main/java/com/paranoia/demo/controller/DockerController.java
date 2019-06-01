package com.paranoia.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PARANOIA_ZK
 * @date 2019/5/29 16:23
 */
@RestController
public class DockerController {

    @GetMapping("/docker")
    public String docker() {
        return "hi,docker!";
    }
}
