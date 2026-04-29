package com.weisizhang.forumbackend.services;

import com.weisizhang.forumbackend.model.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleService {


    /**
     * 发布帖子
     * @param article 要发布的贴子
     */
    @Transactional
    void create(Article article);

    /**
     * 查询所有贴子列表
     * @return 返回贴子列表
     */
    List<Article> selectAll();

    /**
     * 根据板块Id查询贴子列表
     * @param boardId 板块Id
     * @return 返回贴子列表
     */
    List<Article> selectAllByBoardId(Long boardId);

    /**
     *  根据贴子Id 查询贴子详情
     * @param id 贴子Id
     * @return 返回贴子详情
     */
    Article selectDetailById(Long id);


    /**
     *  根据贴子Id, 更新贴子标题和内容
     * @param id 贴子id
     * @param title 贴子标题
     * @param content 贴子内容
     */
    void modify(Long id, String title, String content);

    /**
     *  点赞
     * @param id 贴子Id
     */
    void thumbsUpById(Long id);
}
