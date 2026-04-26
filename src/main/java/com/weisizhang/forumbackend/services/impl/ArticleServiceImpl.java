package com.weisizhang.forumbackend.services.impl;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import com.weisizhang.forumbackend.dao.ArticleMapper;
import com.weisizhang.forumbackend.exception.ApplicationException;
import com.weisizhang.forumbackend.model.Article;
import com.weisizhang.forumbackend.model.Board;
import com.weisizhang.forumbackend.model.User;
import com.weisizhang.forumbackend.services.IArticleService;
import com.weisizhang.forumbackend.services.IBoardService;
import com.weisizhang.forumbackend.services.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;


@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {

    @Resource
    private IUserService userService;
    @Resource
    private IBoardService boardService;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    @Transactional
    public void create(Article article){
        //非空校验
        if (article == null || article.getUserid() == null ||
                article.getBoardId() == 0 ||
                StringUtils.isEmpty(article.getTitle()) ||
                StringUtils.isEmpty(article.getContent())
                ) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        //设置新帖子默认值
        article.setVistCount(0);
        article.setReplyCount(0); // 回复数
        article.setLikeCount(0); // 点 赞数
        article.setDeleteState((byte) 0);
        article.setState((byte) 0);
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);

        //写入数据库
        int articleRow = articleMapper.insert(article);
        if (articleRow <= 0) {
            log.warn(ResultCode.FAILED_CREATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }

        //获取用户信息
        User user = userService.selectById(article.getUserid());
        if (user == null) {
            log.warn(ResultCode.FAILED_CREATE.toString()+", 发贴失败, user id= " + article.getUserid());;
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        userService.addOneArticleCountById(user.getId());

        //板块信息
        Board board = boardService.selectById(user.getId());
        if (board == null) {
            log.warn(ResultCode.FAILED_CREATE.toString()+", 发贴失败, board id= " + article.getBoardId());;
        }
        boardService.addOneArticleCountById(board.getId());

        // 打 印日志
        log.info(ResultCode.SUCCESS.toString() + ", user id = " + article.getUserid()
            + ", board id = " + article.getBoardId() + ", article id =" + article.getId()+
            "发帖成功");
    }
}
