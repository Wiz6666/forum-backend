package com.weisizhang.forumbackend.services.impl;

import com.weisizhang.forumbackend.model.Article;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceImplTest {

    @Resource
    private ArticleServiceImpl articleService;

    @Test
    void create() {
        Article article = new Article();
        article.setUserid(1L);
        article.setBoardId(1L);
        article.setTitle("title");
        article.setContent("content");
        articleService.create(article);
        System.out.println("SUCCESS");
    }
}