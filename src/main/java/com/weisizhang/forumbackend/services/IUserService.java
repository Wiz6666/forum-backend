package com.weisizhang.forumbackend.services;

import com.weisizhang.forumbackend.model.User;
import org.apache.ibatis.annotations.Param;


/**
 * 用户接口
 */
public interface IUserService {

    /**
     * 创建普通用户
     * @param user
     */
    void createNormalUser(@Param("user") User user);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User selectByUserName(String username);
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户对象
     */
    User login(String username, String password);


    /**
     * 查询用户
     * @param id 用户id
     * @return User对象
     */
    User selectById(Long id);


    /**
     * 更新当前用户的发帖数
     * @param id 用户id
     */
    void addOneArticleCountById(Long id);
}
