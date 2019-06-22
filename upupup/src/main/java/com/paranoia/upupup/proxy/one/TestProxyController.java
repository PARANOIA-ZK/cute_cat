package com.paranoia.upupup.proxy.one;

import com.paranoia.upupup.proxy.one.dto.NoExtendsDTO;
import com.paranoia.upupup.proxy.one.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHANGKAI
 * @date 2019/6/14
 * @description
 */
@RestController
@RequestMapping("/proxy")
public class TestProxyController {


    @PostMapping
    public String testAspect(@RequestBody UserDTO userDTO){
        System.out.println("userDTO = " + userDTO);
        System.out.println("userDTO = " + userDTO.getCreatedAt());
        return userDTO.getUserName();
    }


    @PostMapping("no")
    public String testAspectNo(@RequestBody NoExtendsDTO noExtendsDTO){
        System.out.println("noExtendsDTO = " + noExtendsDTO);
        return  noExtendsDTO.getUserName();
    }
}
