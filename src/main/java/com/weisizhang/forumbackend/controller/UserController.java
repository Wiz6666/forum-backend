package com.weisizhang.forumbackend.controller;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import com.weisizhang.forumbackend.config.AppConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import com.weisizhang.forumbackend.model.User;
import com.weisizhang.forumbackend.services.IUserService;
import com.weisizhang.forumbackend.utils.MD5Util;
import com.weisizhang.forumbackend.utils.UUIDUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户登录
     * @param username 用户名
     * @param password  密码
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public AppResult login(
            HttpServletRequest request,
            @Parameter(description = "用户名") @RequestParam("username") @NonNull String username,
            @Parameter(description = "密码") @RequestParam("password") @NonNull String password) {

        //1.调用Service 登陆方法, 返回user对象
        User user = userService.login(username, password);
        if (user == null) {
            log.warn("{}", ResultCode.FAILED_LOGIN);
            return AppResult.failed(ResultCode.FAILED_LOGIN);
        }
        //2.如果登陆成功, 把user对象设置到session对象里
        HttpSession session = request.getSession(true);
        session.setAttribute(AppConfig.USER_SESSION, user);
        //3.返回
        log.info("{}", ResultCode.SUCCESS);
        return AppResult.success();
    }


    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public AppResult logout (HttpServletRequest request) {
        // 获取 session对 象
        HttpSession session = request.getSession();
        if (session != null) {
            log.info("{},退出成功", ResultCode.SUCCESS);
        // 注 销 session
            session.invalidate();
        }
        // 退 出 成功 响 应
        return AppResult.success();
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public AppResult<User> getUserInfo (HttpServletRequest request,
                                        @Parameter(description = "用户Id") @RequestParam(value = "id", required = false) Long id) {
        // 定义返回的User对象
        User user;
        // 根据Id的值判断User对象的获取方式
        if (id == null) {
            // 1. 如果id为空，从session中获取当前登录的用户信息
            HttpSession session = request.getSession(false);

            if(session == null || session.getAttribute(AppConfig.USER_SESSION) == null) {
                return AppResult.failed(ResultCode.FAILED_FORBIDDEN);
            }

            user = (User) session.getAttribute(AppConfig.USER_SESSION);
        } else {
            // 2. 如果id不为空，从数据库中按Id查询出用户信息
            user = userService.selectById(id);
        }
        // 判断用户对象是否为空
        if (user == null) {
            return AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        // 返回正常的结果
        return AppResult.success(user);
    }
}
