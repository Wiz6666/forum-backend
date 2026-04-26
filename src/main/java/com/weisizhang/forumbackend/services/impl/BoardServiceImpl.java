package com.weisizhang.forumbackend.services.impl;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import com.weisizhang.forumbackend.dao.BoardMapper;
import com.weisizhang.forumbackend.exception.ApplicationException;
import com.weisizhang.forumbackend.model.Board;
import com.weisizhang.forumbackend.services.IBoardService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class BoardServiceImpl implements IBoardService {
    @Resource
    private BoardMapper boardMapper;
    @Override
    public List<Board> selectByNum(Integer num) {
        //非空校验
        if (num <= 0){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //调用DAO,直接返回
        List<Board> result = boardMapper.selectByNum(num);
        return result;
    }

    @Override
    public Board selectById(Long id) {
        if (id == null || id <= 0){
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.toString());
            throw new  ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }
        //调用DAO,查询这个ID的板块数据
        Board board = boardMapper.selectByPrimaryKey(id);
        return board;
    }
    @Override
    public void addOneArticleCountById(Long id){
        if (id == null || id <= 0){
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.toString());
            throw new  ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }

        Board board = boardMapper.selectByPrimaryKey(id);
        if (board == null){
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            throw new  ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        Board updateBoard = new Board();
        updateBoard.setId(board.getId());
        updateBoard.setArticleCount(board.getArticleCount() + 1);

        int affectedRows = boardMapper.updateByPrimaryKeySelective(updateBoard);
        if (affectedRows != 1){
            log.warn(ResultCode.FAILED.toString()+"受影响行数不等于1.");
            throw new  ApplicationException(AppResult.failed(ResultCode.FAILED));
        }
    }

}
