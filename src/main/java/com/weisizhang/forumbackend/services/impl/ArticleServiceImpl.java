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
import java.util.List;


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
        article.setVisitCount(0);
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
        Board board = boardService.selectById(article.getBoardId());
        if (board == null) {
            log.warn(ResultCode.FAILED_CREATE.toString()+", 发贴失败, board id= " + article.getBoardId());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        boardService.addOneArticleCountById(board.getId());

        // 打 印日志
        log.info(ResultCode.SUCCESS.toString() + ", user id = " + article.getUserid()
            + ", board id = " + article.getBoardId() + ", article id =" + article.getId()+
            "发帖成功");
    }



    public List<Article> selectAll(){
        //没有参数, 不做非空校验, 结果也不做
        return articleMapper.selectAll();
    }


    public List<Article> selectAllByBoardId(Long boardId) {
        if (boardId == null || boardId <= 0) {
            log.info(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        return articleMapper.selectAllByBoardId(boardId);
    }

    public Article selectDetailById(Long id){
        if (id == null || id <= 0) {
            log.info(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //调用DAO查询结果
        Article result = articleMapper.selectDetailById(id);

        //结果校验
        if (result == null) {
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //更新访问次数
        Article update = new Article();
        update.setId(id);
        update.setVisitCount(result.getVisitCount()+1);
        int row = articleMapper.updateByPrimaryKeySelective(update);
        log.info(ResultCode.SUCCESS.toString() + ", row = " + row + ", article id = " + update.getId());

        //返回结果
        return result;
    }

    /**
     *  根据贴子Id, 更新贴子标题和内容
     * @param id 贴子id
     * @param title 贴子标题
     * @param content 贴子内容
     */
    public void modify(Long id, String title, String content){
        if (id == null || StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        //构造贴子对象
        Article update = new Article();
        update.setId(id);
        update.setTitle(title);
        update.setContent(content);
        update.setUpdateTime(new Date());
        int row = articleMapper.updateByPrimaryKeySelective(update);

        if (row != 1) {
            log.info(ResultCode.ERROR_SERVICES.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

    }
    /**
     *  点赞
     * @param id 贴子Id
     */
    public void thumbsUpById(Long id){

        //非空校验
        if (id == null || id <= 0) {
            log.info(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //获取帖子信息
        Article article = selectDetailById(id);
        if (article == null || article.getDeleteState() != 0) {
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        Article update = new Article();
        update.setId(id);
        update.setLikeCount(article.getLikeCount() +1);
        //更新数据库
        int row = articleMapper.updateByPrimaryKeySelective(update);
        log.info(ResultCode.SUCCESS.toString() + ", row = " + row + ", article id = " + update.getId());
        if (row != 1){
            log.info(ResultCode.FAILED_CREATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }


    }


}
