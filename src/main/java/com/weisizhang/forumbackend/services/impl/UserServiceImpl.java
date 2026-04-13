package com.weisizhang.forumbackend.services.impl;

import com.weisizhang.forumbackend.model.User;
import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import com.weisizhang.forumbackend.dao.UserMapper;
import com.weisizhang.forumbackend.exception.ApplicationException;
import com.weisizhang.forumbackend.services.IUserService;
import com.weisizhang.forumbackend.utils.StringUtil;
import com.weisizhang.forumbackend.utils.UUIDUtil;
import com.weisizhang.forumbackend.utils.MD5Util;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService{ // 添加了 implements 接口
    @Resource
    private UserMapper userMapper;

    @Override
    public void createNormalUser(User user){
        //1.非空校验
        if (user == null ||
                StringUtil.isEmpty(user.getUsername()) ||
                StringUtil.isEmpty(user.getNickname()) ||
                StringUtil.isEmpty(user.getPassword()) ||
                StringUtil.isEmpty(user.getSalt())
        )
        { // 整理了多余的括号
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛出统一Application Exception
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //2.按照用户名查询信息
        User existsUser = userMapper.selectByUserName(user.getUsername());
        //2.1判断是否存在用户
        if  (existsUser != null) {
            log.info(ResultCode.FAILED_USER_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_EXISTS));
        }

        //3.新增用户
        //3.1设置默认值
        user.setGender((byte) 2);
        user.setArticlecount(0);
        user.setIsadmin((byte)0);
        user.setState((byte)0);
        user.setDeletestate((byte)0);
        //（去掉了强行填空字符串的代码，因为老师的表允许为空了）
        
        //设置当前日期
        Date date = new Date();
        user.setCreatetime(date);
        user.setUpdatetime(date);

        //3.2写入数据库
        int row = userMapper.insertSelective(user);
        if (row != 1){
            //打印日志抛出异常
            log.info(ResultCode.FAILED_CREATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        //打印日志
        log.info("新增用户成功, username = {}. ", user.getUsername());
    }
}