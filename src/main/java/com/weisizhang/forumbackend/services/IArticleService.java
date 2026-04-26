package com.weisizhang.forumbackend.services;

import com.weisizhang.forumbackend.model.Article;
import org.springframework.transaction.annotation.Transactional;

public interface IArticleService {


    /**
     * 发布帖子
     * @param article 要发布的贴子
     */
    @Transactional
    void create(Article article);


}
