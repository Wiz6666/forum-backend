package com.weisizhang.forumbackend.services.impl;

import com.weisizhang.forumbackend.model.User;
import com.weisizhang.forumbackend.services.IUserService;
import com.weisizhang.forumbackend.utils.MD5Util;
import com.weisizhang.forumbackend.utils.UUIDUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    void selectByUserName() {
        // 因为我们在建表或者前面测试时可能已经插入了 admin，可以拿来查
        String username = "admin"; // 替换成数据库里实际存在的用户名，比如你之前注册的 admin
        User user = userService.selectByUserName(username);
        
        System.out.println("查询到的用户信息：" + user);
        assertNotNull(user, "查出来的用户不应该为空");
        assertEquals(username, user.getUsername(), "查出来的用户名必须和输入的一样");
    }

    @Test
    void login() {
        // 用刚才注册的账号或者 createNormalUser 里生成的账号进行登录测试
        String username = "admin";
        String password = "123";
        
        User user = userService.login(username, password);
        System.out.println("登录成功的用户信息：" + user);
        assertNotNull(user, "登录成功后返回的用户不应该为空");
    }

    @Test
    void selectById() {
        User user = userService.selectById(1L);
        System.out.println(user);
    }


    @Test
    @Transactional
    void testAddOneArticleCountById() {
        Long userId = 1L;
        User before = userService.selectById(userId);
        assertNotNull(before, "用户不存在，无法测试");
        Integer beforeCount = before.getArticleCount();
        System.out.println("调用前 articleCount = " + beforeCount);

        userService.addOneArticleCountById(userId);

        User after = userService.selectById(userId);
        assertNotNull(after, "更新后查询用户为空");
        Integer afterCount = after.getArticleCount();
        System.out.println("调用后 articleCount = " + afterCount);

        assertEquals(beforeCount + 1, afterCount, "发帖数应该加 1");
        System.out.println("断言通过：articleCount 成功 +1，事务会在测试结束后回滚");
    }
}