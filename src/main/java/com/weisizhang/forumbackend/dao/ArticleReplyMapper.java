package com.weisizhang.forumbackend.dao;

import com.weisizhang.forumbackend.model.ArticleReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleReplyMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleReply row);

    int insertSelective(ArticleReply row);

    ArticleReply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleReply row);

    int updateByPrimaryKey(ArticleReply row);
}