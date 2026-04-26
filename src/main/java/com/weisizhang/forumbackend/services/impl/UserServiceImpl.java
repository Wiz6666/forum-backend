package com.weisizhang.forumbackend.services.impl;

import com.weisizhang.forumbackend.model.User;
import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import com.weisizhang.forumbackend.dao.UserMapper;
import com.weisizhang.forumbackend.exception.ApplicationException;
import com.weisizhang.forumbackend.services.IUserService;
import com.weisizhang.forumbackend.utils.StringUtil;
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
public class UserServiceImpl implements IUserService{ 
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
        user.setArticleCount(0);
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

    @Override
    public User selectByUserName(String username){

        if (StringUtil.isEmpty(username)){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        return userMapper.selectByUserName(username);

    }

    @Override
    public User login(String username, String password){
        //1.非空校验
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //2.按用户名查询用户信息
        User user = selectByUserName(username);
        //3.对用户查询结果做非空校验
        if (user == null){
            log.warn(ResultCode.FAILED_USER_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        //4.密码的校验
        String encryptPassword = MD5Util.md5Salt(password,user.getSalt());
        if (!user.getPassword().equalsIgnoreCase(encryptPassword)){
            log.warn(ResultCode.FAILED_LOGIN.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }
        //5.登陆成功返回
        return user;
    }

    @Override
    public User selectById(Long id){
        //1.非空校验
        if (id == null )
        { // 整理了多余的括号
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛出统一Application Exception
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //调用DAO得到user数据
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public void addOneArticleCountById(Long id){
        if (id==null || id <=0){
            log.warn(ResultCode.FAILED_USER_ARTICLE_COUNT.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_ARTICLE_COUNT));
        }
        //查询用户信息
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null){
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //更新用户发帖数量
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setArticleCount(user.getArticleCount()+1);

        int row = userMapper.updateByPrimaryKeySelective(updateUser);
        if (row != 1){
            log.warn(ResultCode.FAILED.toString()+ "受影响的行数不等于1.");
            throw new  ApplicationException(AppResult.failed(ResultCode.FAILED));
        }
    }
}