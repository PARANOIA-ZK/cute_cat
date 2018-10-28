package com.paranoia.webfluxsecurity.service;

import com.paranoia.webfluxsecurity.collection.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/28 14:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void save() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("张三");
        sysUser.setPassWord("123");
        sysUser.setRole("ADMIN");

        sysUserService.save(sysUser).subscribe(System.out::println);
    }

    @Test
    public void findUserByUserName() {
    }
}