package com.weisizhang.forumbackend.services.impl;

import com.weisizhang.forumbackend.model.Board;
import com.weisizhang.forumbackend.services.IBoardService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardServiceImplTest {
    @Resource
    private IBoardService boardService;
    @Test
    void selectByNum() {
        List<Board> boards = boardService.selectByNum(5);
        System.out.println(boards);
    }

    @Test
    void addOneArticleCountById() {
        boardService.addOneArticleCountById(1L);
        System.out.println(boardService.toString());
    }

    @Test
    @Transactional
    void testAddOneArticleCountById() {
        boardService.addOneArticleCountById(1L);
        System.out.println(boardService.toString());
    }
}