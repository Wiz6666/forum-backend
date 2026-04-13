package com.weisizhang.forumbackend.controller;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import com.weisizhang.forumbackend.model.User;
import com.weisizhang.forumbackend.services.IUserService;
import com.weisizhang.forumbackend.utils.MD5Util;
import com.weisizhang.forumbackend.utils.UUIDUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户接口")
//日志
@Slf4j
//这是一个返回数据的Controller,不是跳转
@RestController
//路径映射,一级路径
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 用户注册
     * @param username 用户名
     * @param nickname 用户昵称
     * @param password 用户密码
     * @param passwordRepeat 确认密码,重复密码
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public AppResult register(
            @Parameter(description = "用户名") @RequestParam("username") @NonNull String username,
            @Parameter(description = "用户昵称") @RequestParam("nickname") @NonNull String nickname,
            @Parameter(description = "用户密码") @RequestParam("password") @NonNull String password,
            @Parameter(description = "重复密码") @RequestParam("passwordRepeat") @NonNull String passwordRepeat) {
        
        // 校验密码与重复密码是否相同 (注：参数的空校验已经被 @RequestParam 默认必传属性拦截了很大一部分)
        if (!password.equals(passwordRepeat)) {
            log.warn("{}", ResultCode.FAILED_TWO_PWD_NOT_SAME);
            // 返回错误信息
            return AppResult.failed(ResultCode.FAILED_TWO_PWD_NOT_SAME);
        }

        // 准备数据
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);

        // 处理密码
        // 1. 生成盐
        String salt = UUIDUtil.UUID_32();
        // 2. 生成密码的密文
        String encryptPassword = MD5Util.md5Salt(password, salt);
        // 3. 设置密码和盐
        user.setPassword(encryptPassword);
        user.setSalt(salt);

        // 调用Service层
        userService.createNormalUser(user);
        // 返回成功
        return AppResult.success();
    }
}
