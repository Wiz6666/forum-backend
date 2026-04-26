package com.weisizhang.forumbackend.dao;

import com.weisizhang.forumbackend.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Board row);

    int insertSelective(Board row);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board row);

    int updateByPrimaryKey(Board row);

    List<Board> selectByNum(@Param("num") Integer num);
}