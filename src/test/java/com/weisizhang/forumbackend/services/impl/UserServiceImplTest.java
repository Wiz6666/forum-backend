package com.weisizhang.forumbackend.services.impl;

import com.weisizhang.forumbackend.model.User;
import com.weisizhang.forumbackend.services.IUserService;
import com.weisizhang.forumbackend.utils.MD5Util;
import com.weisizhang.forumbackend.utils.UUIDUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private IUserService userService;

    @Test
    void createNormalUser() {

        //先做构造User对象
        User user = new User();
        user.setUsername("username");
        user.setNickname("user-nickname");

        String password = "password";
        String salt = UUIDUtil.UUID_32();
        String ciphertext = MD5Util.md5Salt(password,salt);
        user.setPassword(ciphertext);
        user.setSalt(salt);

        userService.createNormalUser(user);
        // 打印结果
        System.out.println(user);
    }
}