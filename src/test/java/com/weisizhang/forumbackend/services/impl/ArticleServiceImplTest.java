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

    @Test
    void thumbsUpById_shouldIncreaseLikeCount() {
        // 前置：先创建一条帖子
        Article article = new Article();
        article.setUserid(1L);
        article.setBoardId(1L);
        article.setTitle("test thumbs up");
        article.setContent("content");
        articleService.create(article);

        Long id = article.getId();
        assertNotNull(id, "新建帖子ID不能为空");

        // 获取点赞前的 likeCount
        Article before = articleService.selectDetailById(id);
        Integer beforeLike = before.getLikeCount();
        if (beforeLike == null) beforeLike = 0;

        // 执行点赞
        articleService.thumbsUpById(id);

        // 获取点赞后的 likeCount
        Article after = articleService.selectDetailById(id);
        assertEquals(beforeLike + 1, after.getLikeCount());
    }

    @Test
    void thumbsUpById_shouldThrowException_whenIdIsNull() {
        Exception ex = assertThrows(Exception.class, () -> articleService.thumbsUpById(null));
        assertTrue(ex.getMessage().contains("ERROR_IS_NULL"));
    }
}