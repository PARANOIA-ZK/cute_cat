package com.paranoia.upupup.aspect.one;

import com.paranoia.upupup.aspect.one.dto.NoExtendsDTO;
import com.paranoia.upupup.aspect.one.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHANGKAI
 * @date 2019/6/14
 * @description
 */
@RestController
public class AspectController {


    @PostMapping("/aspect")
    public String testAspect(@RequestBody UserDTO userDTO){
        System.out.println("userDTO = " + userDTO);
        System.out.println("userDTO = " + userDTO.getCreatedAt());
        return userDTO.getUserName();
    }


    @PostMapping("/aspect/no")
    public String testAspectNo(@RequestBody NoExtendsDTO noExtendsDTO){
        System.out.println("noExtendsDTO = " + noExtendsDTO);
        return  noExtendsDTO.getUserName();
    }
}
