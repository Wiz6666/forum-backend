package com.weisizhang.forumbackend.dao;

import com.weisizhang.forumbackend.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Article row);

    int insertSelective(Article row);


    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article row);

    int updateByPrimaryKeyWithBLOBs(Article row);

    int updateByPrimaryKey(Article row);

    /**
     * 查询所有贴子列表
     * @return 返回贴子列表
     */
    List<Article>  selectAll();

    /**
     * 根据板块Id查询贴子列表
     * @param boardId 板块Id
     * @return 返回贴子列表
     */
    List<Article> selectAllByBoardId(@Param("boardId") Long boardId);

    /**
     *  根据贴子Id 查询贴子详情
     * @param id 贴子Id
     * @return 返回贴子详情
     */
    Article selectDetailById(Long id);
}