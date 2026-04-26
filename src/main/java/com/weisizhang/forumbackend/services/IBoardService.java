package com.weisizhang.forumbackend.services;

import com.weisizhang.forumbackend.model.Board;

import java.util.List;

public interface IBoardService {

    /**
     * 查询num条记录
     * @param num 查询num条记录
     * @return
     */
    List<Board> selectByNum(Integer num);

    /**
     * 根据板块Id查询板块信息
     * @param id 板块id
     * @return
     */
    Board selectById(Long id);

    /**
     * 板块中贴子数量+1
     * @param id 板块Id
     */
    void addOneArticleCountById(Long id);


}
