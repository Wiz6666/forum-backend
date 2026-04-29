package com.weisizhang.forumbackend.controller;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import com.weisizhang.forumbackend.exception.ApplicationException;
import com.weisizhang.forumbackend.model.Board;
import com.weisizhang.forumbackend.services.IBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/getById")
    @Operation(summary = "获取板块信息")
    public AppResult<Board> selectById(
            @Parameter(description = "板块ID")
            @RequestParam(value = "id", required = false)
            @NonNull
            Long id){


        Board board = boardService.selectById(id);
        if(board == null){
            log.warn(ResultCode.FAILED_BOARD_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_NOT_EXISTS));
        }
        return AppResult.success(board);
    }

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
