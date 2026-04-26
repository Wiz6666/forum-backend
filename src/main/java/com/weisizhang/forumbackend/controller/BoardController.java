package com.weisizhang.forumbackend.controller;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.model.Board;
import com.weisizhang.forumbackend.services.IBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "板块接口")
@RestController
@RequestMapping("/board")
public class BoardController  {
    @Value("${index.board-num}")
    private Integer indexBoardNum;


    @Resource
    IBoardService boardService;

    /**
     * 查询首页板块列表
     * @return
     */
    @GetMapping("/topList")
    @Operation(summary = "查询首页板块列表")
    public AppResult<List<Board>> toplist(){
        log.info("首页板块个数为"+indexBoardNum);
        List<Board> boards = boardService.selectByNum(indexBoardNum);

        //判断是否空
        if(boards == null){
            boards = new ArrayList<>();
        }

        return AppResult.success(boards);
    }
}
