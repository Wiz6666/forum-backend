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

}
