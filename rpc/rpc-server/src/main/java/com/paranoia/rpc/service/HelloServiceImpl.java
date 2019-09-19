package com.paranoia.rpc.service;

import com.paranoia.api.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author ZHANGKAI
 * @date 2019/9/18
 * @description
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String name, int age) {
        return "你好," + name + ",你的年龄是：" + age;
    }
}
